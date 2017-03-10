package msrcpsp.scheduling;

import msrcpsp.evaluation.BaseEvaluator;

import java.util.List;

/**
 * Single individual in a population used.
 * Stores information about schedule and evaluation properties
 * along with double typed genes.
 */
public class BaseDoubleIndividual extends BaseIndividual {

  private double[] genes;

  public BaseDoubleIndividual(Schedule schedule, double genes[], BaseEvaluator evaluator) {
    super(schedule, evaluator);
    this.genes = new double[schedule.getTasks().length];
    System.arraycopy(genes, 0, this.genes, 0, genes.length);
  }

  public double[] getGenes() {
    return genes;
  }

  public void setGenes(double[] genes) {
    this.genes = genes;
  }

}
