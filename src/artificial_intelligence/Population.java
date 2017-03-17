package artificial_intelligence;

import msrcpsp.evaluation.BaseEvaluator;
import msrcpsp.evaluation.DurationEvaluator;

import java.util.ArrayList;

/**
 * @author Jagoda Marszałek
 * @version 1.0.0
 * @since 11.03.2017
 */
public class Population {
    private ArrayList<Individual> individuals;
    private int id;
    private double bestTime;
    private double worstTime;
    private double sumTime;
    private double avgTime;

    /**
     * Population constructor.
     *
     * @param individuals - population of schedules
     * @param id          - population id
     */
    Population(ArrayList<Individual> individuals, int id) {
        this.individuals = individuals;
        this.id = id;
    }

    /**
     * Population constructor.
     */
    Population(int id) {
        this.individuals = new ArrayList<>();
        this.id = id;
    }

    /**
     * Add new individual to population
     *
     * @param individual - individual
     */
    void addNewIndividual(Individual individual) {
        this.individuals.add(individual);
    }

    /**
     * To String method
     *
     * @return String
     */
    public String toString() {
        return "Id: " + this.id + ", bestTime: " + this.bestTime + ", worstTime: " + this.worstTime + ", avgTime: "
                + this.avgTime + ", sum time: " + this.sumTime;
    }

    /**
     * Evaluate schedules duration.
     */
    void evaluateDuration() {
        double bestTime = 0;
        double worstTime = 0;
        double sumTime = 0;

        for (Individual individual : this.individuals) {
            BaseEvaluator evaluator = new DurationEvaluator(individual.getSchedule());
            double duration = evaluator.evaluate();
            individual.setFitness(duration);
            sumTime += duration;
            if (duration < bestTime || 0 == bestTime) {
                bestTime = duration;
            }
            if (duration > worstTime || 0 == worstTime) {
                worstTime = duration;
            }
        }

        this.bestTime = bestTime;
        this.worstTime = worstTime;
        this.sumTime = sumTime;
        this.avgTime = sumTime/individuals.size();
    }

    /**
     * Get population id
     *
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Set population id
     *
     * @param id - population id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get best schedule time in population
     *
     * @return int
     */
    public double getBestTime() {
        return bestTime;
    }

    /**
     * Set best schedule time
     *
     * @param bestTime - best schedule time in population
     */
    public void setBestTime(double bestTime) {
        this.bestTime = bestTime;
    }

    /**
     * Get average schedule time in population
     *
     * @return int
     */
    public double getAvgTime() {
        return avgTime;
    }

    /**
     * Set average schedule time in population
     *
     * @param avgTime - average schedule time in population
     */
    public void setAvgTime(double avgTime) {
        this.avgTime = avgTime;
    }

    /**
     * Get worst schedule time in population
     *
     * @return int
     */
    public double getWorstTime() {
        return worstTime;
    }

    /**
     * Set worst schedule time in population
     *
     * @param worstTime - worst schedule time in population
     */
    public void setWorstTime(double worstTime) {
        this.worstTime = worstTime;
    }

    /**
     * Get individuals
     *
     * @return Individual
     */
    public ArrayList<Individual> getIndividuals() {
        return individuals;
    }

    /**
     * Set individuals
     *
     * @param individuals - individuals
     */
    public void setIndividuals(ArrayList<Individual> individuals) {
        this.individuals = individuals;
    }
}
