package artificial_intelligence;

import msrcpsp.evaluation.BaseEvaluator;
import msrcpsp.evaluation.DurationEvaluator;
import msrcpsp.scheduling.Schedule;

/**
 * @author Jagoda Marszałek
 * @version 1.0.0
 * @since 11.03.2017
 */
public class Population {
    private Schedule[] schedules;
    private int id;
    private double bestTime;
    private double worstTime;
    private double sumTime;
    private double avgTime;

    /**
     * Population constructor.
     *
     * @param schedules - population of schedules
     * @param id        - population id
     */
    Population(Schedule[] schedules, int id) {
        this.schedules = schedules;
        this.id = id;
    }

    /**
     * To String method
     *
     * @return String
     */
    public String toString() {
        return "Id: " + this.id + ", bestTime: " + this.bestTime + ", worstTime: " + this.worstTime + ", avgTime: "
                + this.avgTime;
    }

    /**
     * Evaluate schedules duration.
     */
    void evaluateDuration() {
        double bestTime = 0;
        double worstTime = 0;
        double sumTime = 0;

        for (Schedule schedule : this.schedules) {
            BaseEvaluator evaluator = new DurationEvaluator(schedule);
            double duration = evaluator.evaluate();
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
        this.avgTime = sumTime / schedules.length;
    }

    /**
     * Get schedules
     *
     * @return Schedule[]
     */
    public Schedule[] getSchedules() {
        return schedules;
    }

    /**
     * Set schedules
     *
     * @param schedules - population of schedules
     */
    public void setSchedules(Schedule[] schedules) {
        this.schedules = schedules;
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
}
