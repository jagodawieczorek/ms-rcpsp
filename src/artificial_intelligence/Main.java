package artificial_intelligence;

/**
 * Genetic Algorithm class
 *
 * @author Jagoda Marszałek
 * @version 1.0.0
 * @since 10.03.2017
 */
public class Main {
    private static final String TEST_DATA = "assets/def_small/10_7_10_7.def";

    /**
     * Main function
     *
     * @param args - arguments
     */
    public static void main(String[] args) {
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(TEST_DATA);
        int generation = 0;
        Population population = geneticAlgorithm.initializePopulation(0);
        population.evaluateDuration();
        System.out.println(population.toString());

        while (generation < geneticAlgorithm.getGenerations()) {
            generation++;
            population = geneticAlgorithm.createNewPopulation(population, generation);
            population.evaluateDuration();
            System.out.println(population.toString());
        }
    }
}