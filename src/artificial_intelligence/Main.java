package artificial_intelligence;

/**
 * Genetic Algorithm class
 *
 * @author Jagoda Marszałek
 * @version 1.0.0
 * @since 10.03.2017
 */
public class Main {
    private static final String TEST_DATA = "assets/def_small/10_5_8_5.def";

    /**
     * Main function
     *
     * @param args - arguments
     */
    public static void main(String[] args) {
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(TEST_DATA);
        int generation = 0;
        Population population0 = geneticAlgorithm.initializePopulation(0);
        population0.evaluateDuration();
        System.out.println(population0.toString());

        while (generation < geneticAlgorithm.getGenerations()) {
            Population population = geneticAlgorithm.initializePopulation(generation + 1);
            population.evaluateDuration();
            System.out.println(population.toString());
            generation += 1;
        }
    }
}