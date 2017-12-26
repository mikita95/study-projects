import Experiment.ExperimentSeries

object Main {
  def main(args: Array[String]): Unit = {
    eqSystem1

  }

  def eqSystem1 = {
    val R = 8314.4621
    val sigma = 0.01
    val experiments: Experiment.ExperimentSeries = new ExperimentSeries(350 + 273, 650 + 273, 10).mapX(x => 1d / x).mapY(y => math.log(-y))
    val solver: Double => Vector = new CachingWrapper[Double, Vector](Systems.system1(new Vector(Array[Double](50d, 50d, 50d, 50d, 500d)), 1e-5, 10000))
    val countG = (index: Int, t: Double) => solver(t)(index) / R / t / sigma * Coefficients.getDEvaluator("AlCl" + (if (index == 0) "" else (index + 1) + ""))(t)

    new Graphic("T^-1", "ln -V_Al").
      addGraphic(experiments.create(
        t => List(0, 1, 2).map(
          index => countG(index, t)).sum
          * Coefficients.getMolarValue("Al") * 1e9).get, "V_Al").show
    new Graphic("T^-1", "ln -G")
      .addGraphic(experiments.create(t => countG(0, t)).get, "AlCL")
      .addGraphic(experiments.create(t => countG(1, t)).get, "AlCL2")
      .addGraphic(experiments.create(t => countG(2, t)).get, "AlCL3").show
  }
}
