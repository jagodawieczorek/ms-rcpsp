package artificial_intelligence;

import msrcpsp.evaluation.DurationEvaluator;
import msrcpsp.io.MSRCPSPIO;
import msrcpsp.scheduling.Resource;
import msrcpsp.scheduling.Schedule;
import msrcpsp.scheduling.Task;
import msrcpsp.scheduling.greedy.Greedy;
import msrcpsp.validation.BaseValidator;
import msrcpsp.validation.CompleteValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Genetic Algorithm class
 *
 * @author Jagoda Marszałek
 * @version 1.0.0
 * @since 11.03.2017
 */
public class GeneticAlgorithm {
    private int popSize;
    private int generations;
    private double mutationProbability;
    private double crossoverProbability;
    private String filename;
    private Integer tn;
    private Schedule baseSchedule;
    private Population newPopulation;
    private static final int DEFAULT_POP_SIZE = 300;
    private static final int DEFAULT_GENERATIONS = 100;
    private static final double DEFAULT_MUTATION_PROBABILITY = 0.01;
    private static final double DEFAULT_CROSSOVER_PROBABILITY = 0.1;
    private static final int DEFAULT_TOURNAMENT_SIZE = 5;

    /**
     * Genetic Algorithm constructor.
     *
     * @param popSize              - population size
     * @param generations          - the number of generations
     * @param mutationProbability  - mutation probability
     * @param crossoverProbability - crossover probability
     * @param filename             - test data filename
     */
    GeneticAlgorithm(int popSize, int generations, double mutationProbability, double crossoverProbability,
                     String filename, int tn) {
        this.popSize = popSize;
        this.generations = generations;
        this.mutationProbability = mutationProbability;
        this.crossoverProbability = crossoverProbability;
        this.filename = filename;
        this.baseSchedule = this.getBaseSchedule(filename);
        this.tn = tn;
    }

    /**
     * Genetic Algorithm constructor. Set default values.
     *
     * @param filename -  test data filename
     */
    GeneticAlgorithm(String filename) {
        this.popSize = DEFAULT_POP_SIZE;
        this.generations = DEFAULT_GENERATIONS;
        this.mutationProbability = DEFAULT_MUTATION_PROBABILITY;
        this.crossoverProbability = DEFAULT_CROSSOVER_PROBABILITY;
        this.filename = filename;
        this.baseSchedule = this.getBaseSchedule(filename);
        this.tn = DEFAULT_TOURNAMENT_SIZE;
    }

    /**
     * Initialize schedule.
     *
     * @param filename - name of .def file
     * @return Schedule
     */
    Schedule getBaseSchedule(String filename) {
        MSRCPSPIO msrcpspio = new MSRCPSPIO();

        return msrcpspio.readDefinition(filename);
    }

    /**
     * Random initialize schedule. Random assign tasks to capable resources.
     *
     * @return Schedule
     */
    Schedule randomInitializeSchedule() {
        // @TODO - refactor. Create deep copy of base schedule object.
        Schedule schedule = this.getBaseSchedule(filename);
        Task[] tasks = schedule.getTasks();
        Random generator = new Random();

        for (Task task : tasks) {
            List<Resource> resources = schedule.getCapableResources(task);
            Resource resource = resources.get(generator.nextInt(resources.size()));
            schedule.assign(task, resource);
        }

        return this.buildTimestamps(schedule);
    }

    /**
     * Assign time to tasks and resources. Greedy algorithm.
     *
     * @param schedule - schedule
     * @return Schedule
     */
    Schedule buildTimestamps(Schedule schedule) {
        Greedy greedy = new Greedy(schedule.getSuccesors());
        schedule.setEvaluator(new DurationEvaluator(schedule));
        greedy.buildTimestamps(schedule);

        return schedule;
    }

    /**
     * Initialize population
     *
     * @param id - population id
     * @return Population
     */
    Population initializePopulation(int id) {
        ArrayList<Individual> individuals = new ArrayList<>();

        for (int i = 0; i < this.getPopSize(); i++) {
            Schedule schedule = this.randomInitializeSchedule();
            individuals.add(this.initializeIndividual(schedule));
        }

        return new Population(individuals, id);
    }

    /**
     * Initialize individual
     *
     * @param schedule - schedule
     * @return Individual
     */
    Individual initializeIndividual(Schedule schedule) {
        Individual individual = new Individual(schedule);

        return individual;
    }

    /**
     * Validate schedule
     *
     * @param schedule - schedule
     */
    void validateSchedule(Schedule schedule) {
        BaseValidator validator = new CompleteValidator();
        System.out.println(validator.validate(schedule));
        System.out.println(validator.getErrorMessages());
    }

    /**
     * Create new population.
     *
     * @param population - population
     * @param id         - population id
     * @return Population
     */
    Population createNewPopulation(Population population, int id) {
        this.setNewPopulation(new Population(id));
        Integer currentIndividual = 0;

        while (currentIndividual < this.getPopSize()) {
            Individual individual = this.select(population);

            if (this.checkAction(this.crossoverProbability)) {
                Individual parent2 = this.select(population);
                individual = this.crossover(individual, parent2);
            }

            if (this.checkAction(this.mutationProbability)) {
                individual = this.mutate(individual);
            }

            Schedule schedule = this.buildTimestamps(individual.getSchedule());
            this.newPopulation.addNewIndividual(this.initializeIndividual(schedule));
            currentIndividual++;
        }

        return this.newPopulation;
    }

    /**
     * Select winner of tournament
     *
     * @param population - old population
     * @return Individual
     */
    Individual select(Population population) {
        Selector selector = new Selector(this.getTn(), population);
        Individual individual = new Individual(selector.tournament());

        return individual;
    }

    /**
     * Crossover method.
     *
     * @param parent  - parent 1
     * @param parent2 - parent 2
     * @return Individual - child
     */
    Individual crossover(Individual parent, Individual parent2) {
        Task[] parentTasks = parent.getSchedule().getTasks();
        Task[] parent2Tasks = parent2.getSchedule().getTasks();
        Random generator = new Random();
        Integer crossoverPoint = generator.nextInt(parentTasks.length);
        Task[] childTasks = new Task[parentTasks.length];

        Integer currentTask = 0;
        while (currentTask < childTasks.length) {
            if (currentTask < crossoverPoint) {
                childTasks[currentTask] = parentTasks[currentTask];
            } else {
                childTasks[currentTask] = parent2Tasks[currentTask];
            }
            currentTask++;
        }

        Schedule child = parent.getSchedule();
        child.setTasks(childTasks);
        Individual individual = new Individual(child);

        return individual;
    }

    /**
     * Mutate method
     *
     * @param individual - individual
     * @return Individual
     */
    Individual mutate(Individual individual) {
        Schedule schedule = individual.getSchedule();
        Task[] tasks = schedule.getTasks();
        Random generator = new Random();
        Integer taskToMutate = generator.nextInt(tasks.length);
        List<Resource> resources = schedule.getCapableResources(tasks[taskToMutate]);
        Resource resource = resources.get(generator.nextInt(resources.size()));
        individual.getSchedule().assign(tasks[taskToMutate], resource);

        return individual;
    }

    /**
     * Check if should do crossover / mutation
     *
     * @param probability - probability for true
     * @return boolean
     */
    boolean checkAction(double probability) {
        return Math.random() > 1.0 - probability;
    }

    /**
     * Get population size.
     *
     * @return int
     */
    public int getPopSize() {
        return popSize;
    }

    /**
     * Set population size.
     *
     * @param popSize - population size
     */
    public void setPopSize(int popSize) {
        this.popSize = popSize;
    }

    /**
     * Get number of generations.
     *
     * @return int
     */
    public int getGenerations() {
        return generations;
    }

    /**
     * Set number of generations
     *
     * @param generations - number of generations
     */
    public void setGenerations(int generations) {
        this.generations = generations;
    }

    /**
     * Get mutation probability.
     *
     * @return double
     */
    public double getMutationProbability() {
        return mutationProbability;
    }

    /**
     * Set mutation probability.
     *
     * @param mutationProbability - mutation probability
     */
    public void setMutationProbability(double mutationProbability) {
        this.mutationProbability = mutationProbability;
    }

    /**
     * Get crossover probability.
     *
     * @return double
     */
    public double getCrossoverProbability() {
        return crossoverProbability;
    }

    /**
     * Set crossover probability.
     *
     * @param crossoverProbability - crossover probability
     */
    public void setCrossoverProbability(double crossoverProbability) {
        this.crossoverProbability = crossoverProbability;
    }

    /**
     * Get tn
     *
     * @return Integer
     */
    public Integer getTn() {
        return tn;
    }

    /**
     * Set tn
     *
     * @param tn - tn
     */
    public void setTn(Integer tn) {
        this.tn = tn;
    }

    /**
     * Get new population
     *
     * @return Population
     */
    public Population getNewPopulation() {
        return newPopulation;
    }

    /**
     * Set new population
     *
     * @param newPopulation - new population
     */
    public void setNewPopulation(Population newPopulation) {
        this.newPopulation = newPopulation;
    }
}
