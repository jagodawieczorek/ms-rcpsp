package artificial_intelligence;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/**
 * Selector class - tournament method
 *
 * @author Jagoda Marszałek
 * @version 1.0.0
 * @since 17.03.2017
 */
public class Selector {
    private Integer tn;
    private Population population;
    private Comparator<Individual> comparator;

    /**
     * Constructor.
     *
     * @param tn         - tournament size
     * @param population - population
     */
    public Selector(Integer tn, Population population) {
        this.tn = tn;
        this.population = population;
        this.comparator = Comparator.comparingDouble(Individual::getFitness);
    }

    /**
     * Tournament method.
     * Select one candidate.
     *
     * @return Individual
     */
    Individual tournament() {
        Random generator = new Random();
        ArrayList<Individual> individuals = this.population.getIndividuals();
        ArrayList<Individual> candidates = new ArrayList<>();
        Integer currentCandidate = 0;

        while (currentCandidate < this.tn) {
            Individual individual = individuals.get(generator.nextInt(individuals.size()));
            candidates.add(individual);
            currentCandidate++;
        }

        candidates.sort(comparator);

        return candidates.get(0);
    }

    /**
     * Get new population
     *
     * @return Population
     */
    public Population getNewPopulation() {
        return population;
    }

    /**
     * Set new population
     *
     * @param newPopulation - new population
     */
    public void setNewPopulation(Population newPopulation) {
        this.population = newPopulation;
    }
}