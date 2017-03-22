package artificial_intelligence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Genetic Algorithm class
 *
 * @author Jagoda Marszałek
 * @version 1.0.0
 * @since 10.03.2017
 */
public class Main {
    private static final String TEST_DATA = "assets/def_small/200_20_55_9.def";

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
        try {
            PrintWriter pw = new PrintWriter(new File("assets/ms-rcpsp.csv"));
            StringBuilder sb = new StringBuilder();
            sb.append("id");
            sb.append(';');
            sb.append("best");
            sb.append(';');
            sb.append("worst");
            sb.append(';');
            sb.append("avg");
            sb.append('\n');

            while (generation < geneticAlgorithm.getGenerations()) {
                generation++;
                population = geneticAlgorithm.createNewPopulation(population, generation);
                population.evaluateDuration();
                sb.append(population.toString());
            }

            pw.write(sb.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}