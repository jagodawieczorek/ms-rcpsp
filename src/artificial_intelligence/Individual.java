package artificial_intelligence;

import msrcpsp.scheduling.Schedule;

/**
 * Individual class
 *
 * @author Jagoda Marszałek
 * @version 1.0.0
 * @since 17.03.2017
 */
public class Individual {
    private Schedule schedule;
    private double fitness;

    /**
     * Individual constructor.
     *
     * @param schedule - individual's schedule
     */
    public Individual(Schedule schedule) {
        this.schedule = schedule;
    }

    /**
     * Copy constructor.
     *
     * @param another - another Individual
     */
    public Individual(Individual another) {
        this.schedule = new Schedule(another.schedule);
    }

    /**
     * Get schedule
     *
     * @return Schedule
     */
    public Schedule getSchedule() {
        return schedule;
    }

    /**
     * Set schedule
     *
     * @param schedule - schedule
     */
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    /**
     * Get fitness
     *
     * @return double
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * Set fitness
     *
     * @param fitness - fitness
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
}
