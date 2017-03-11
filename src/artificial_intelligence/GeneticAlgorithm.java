package artificial_intelligence;

import msrcpsp.io.MSRCPSPIO;
import msrcpsp.scheduling.Resource;
import msrcpsp.scheduling.Schedule;
import msrcpsp.scheduling.Task;

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
    private Schedule baseSchedule;
    private static final int DEFAULT_POP_SIZE = 100;
    private static final int DEFAULT_GENERATIONS = 100;
    private static final double DEFAULT_MUTATION_PROBABILITY = 0.01;
    private static final double DEFAULT_CROSSOVER_PROBABILITY = 0.1;

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
                     String filename) {
        this.popSize = popSize;
        this.generations = generations;
        this.mutationProbability = mutationProbability;
        this.crossoverProbability = crossoverProbability;
        this.filename = filename;
        this.baseSchedule = this.getBaseSchedule(filename);
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

        return schedule;
    }

    /**
     * Initialize population
     *
     * @return Population
     */
    Population initializePopulation() {
        Schedule[] schedules = new Schedule[this.getPopSize()];

        for (int i = 0; i < this.getPopSize(); i++) {
            schedules[i] = this.randomInitializeSchedule();
        }

        return new Population(schedules);
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
}
