package test.validation;

import msrcpsp.io.MSRCPSPIO;
import org.junit.Test;
import msrcpsp.scheduling.Schedule;
import msrcpsp.scheduling.Task;
import msrcpsp.validation.AssignmentValidator;
import msrcpsp.validation.BaseValidator;
import msrcpsp.validation.ValidationResult;

import static org.junit.Assert.*;


public class AssignmentValidatorTest {

  @Test
  public void testValidate() {
    MSRCPSPIO reader = new MSRCPSPIO();
    Schedule schedule = reader.readDefinition("assets/test/10_7_10_7.def");
    assertNotNull("Schedule was not readDefinition correctly", schedule);

    BaseValidator validator = new AssignmentValidator();
    assertEquals("Assignment constraint should be violated",
        ValidationResult.FAILURE, validator.validate(schedule));

    for (Task task : schedule.getTasks()) {
      schedule.assign(task, schedule.getResource(1));
    }

    assertEquals("Assignment constraint should not be violated",
        ValidationResult.SUCCESS, validator.validate(schedule));

  }

}