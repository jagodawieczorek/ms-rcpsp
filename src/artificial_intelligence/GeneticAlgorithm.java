package artificial_intelligence;

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
     */
    public GeneticAlgorithm(int popSize, int generations, double mutationProbability, double crossoverProbability) {
        this.popSize = popSize;
        this.generations = generations;
        this.mutationProbability = mutationProbability;
        this.crossoverProbability = crossoverProbability;
    }

    /**
     * Genetic Algorithm constructor. Set default values.
     */
    public GeneticAlgorithm() {
        this.popSize = DEFAULT_POP_SIZE;
        this.generations = DEFAULT_GENERATIONS;
        this.mutationProbability = DEFAULT_MUTATION_PROBABILITY;
        this.crossoverProbability = DEFAULT_CROSSOVER_PROBABILITY;
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
