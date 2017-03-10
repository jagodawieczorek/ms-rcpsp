import msrcpsp.evaluation.BaseEvaluator;
import msrcpsp.evaluation.DurationEvaluator;
import msrcpsp.io.MSRCPSPIO;
import msrcpsp.scheduling.Resource;
import msrcpsp.scheduling.Schedule;
import msrcpsp.scheduling.Task;
import msrcpsp.scheduling.greedy.Greedy;
import msrcpsp.validation.BaseValidator;
import msrcpsp.validation.CompleteValidator;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Runner class to help with understanding of the library.
 */
public class Runner {

  private static final Logger LOGGER = Logger.getLogger( Runner.class.getName() );
  private static final String definitionFile = "assets/def_small/10_3_5_3.def";
  private static final String writeFile = "assets/solutions_small/10_3_5_3.sol";

  public static void main(String[] args) {

    // read definition file into Schedule object
    MSRCPSPIO reader = new MSRCPSPIO();
    Schedule schedule = reader.readDefinition(definitionFile);
    if (null == schedule) {
      LOGGER.log(Level.WARNING, "Could not read the Definition " + definitionFile);
    }

    // get array of upper bounds of each task assignment, that does
    // violate skill constraint
    int[] upperBounds = schedule.getUpperBounds(schedule.getTasks().length);
    // create an evaluator
    BaseEvaluator evaluator = new DurationEvaluator(schedule);

    Task[] tasks = schedule.getTasks();
    Resource[] resources = schedule.getResources();

    // create arbitrary schedule
    schedule.assign(tasks[0], resources[0]);
    schedule.assign(tasks[1], resources[1]);
    schedule.assign(tasks[2], resources[2]);
    schedule.assign(tasks[3], resources[0]);
    schedule.assign(tasks[4], resources[1]);
    schedule.assign(tasks[5], resources[2]);
    schedule.assign(tasks[6], resources[0]);
    schedule.assign(tasks[7], resources[1]);
    schedule.assign(tasks[8], resources[2]);
    schedule.assign(tasks[9], resources[0]);

    // create greedy algorithm to set timestamps
    Greedy greedy = new Greedy(schedule.getSuccesors());
    // set starts of each task using greedy algorithm
    greedy.buildTimestamps(schedule);

    // validate schedule, it results in a failure (keep in mind, that
    // the schedule was created by hand)
    BaseValidator validator = new CompleteValidator();
    System.out.println(validator.validate(schedule));
    System.out.println(validator.getErrorMessages());

    // create schedule randomly, while keeping constraints
    Random random = new Random(System.currentTimeMillis());
    List<Resource> capableResources;
    for (int i = 0; i < tasks.length; ++i) {
      // get resources capable of performing given task
      capableResources = schedule.getCapableResources(tasks[i]);
      // assign the task to random capable resource
      schedule.assign(tasks[i], capableResources.get((int)(random.nextDouble() * upperBounds[i])));
    }
    // set timestamps in greedy manner
    greedy.buildTimestamps(schedule);
    // validate schedule, this time it results in a success
    System.out.println(validator.validate(schedule));
    System.out.println(validator.getErrorMessages());

    // save to a file
    try {
      reader.write(schedule, writeFile);
    } catch (IOException e) {
      System.out.print("Writing to a file failed");
    }
  }

}
