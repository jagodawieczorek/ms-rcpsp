package artificial_intelligence;

/**
 * Genetic Algorithm class
 *
 * @author Jagoda Marszałek
 * @version 1.0.0
 * @since 10.03.2017
 */
public class Main {
    private static final String TEST_DATA = "assets/def_small/10_3_5_3.def";

    /**
     * Main function
     *
     * @param args - arguments
     */
    public static void main(String[] args) {
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(TEST_DATA);
        Population population = geneticAlgorithm.initializePopulation();
    }
}