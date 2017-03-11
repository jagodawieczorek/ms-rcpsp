package artificial_intelligence;

import msrcpsp.scheduling.Schedule;

/**
 * @author Jagoda Marszałek
 * @version 1.0.0
 * @since 11.03.2017
 */
public class Population {
    private Schedule[] schedules;

    /**
     * Population constructor.
     *
     * @param schedules - population of schedules
     */
    Population(Schedule[] schedules) {
        this.schedules = schedules;
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
}
