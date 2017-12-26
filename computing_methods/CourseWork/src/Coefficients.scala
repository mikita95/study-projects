object Coefficients {
  private var coefficients: Map[String, ElementCoefficients] = Map()
  private var densities: Map[String, Double] = Map()
  private var kEvaluator: Map[Int, Double => Double] = Map()
  registerElementCoefficients("AlCl", Array[Double](-51032.0, 318.9948, 36.94626, -0.001226431, 1.1881743, 5.638541, -5.066135, 5.219347, 62.4345, 3.58, 932.0))
  registerElementCoefficients("AlCl2", Array[Double](-259000.0, 427.2137, 56.56409, -0.002961273, 1.893842, 12.40072, -22.65441, 21.29898, 97.8875, 5.3, 825.0))
  registerElementCoefficients("AlCl3", Array[Double](-584100.0, 511.8114, 81.15042, -0.004834879, 2.752097, 13.40078, -21.28001, 16.92868, 133.3405, 5.13, 472.0))
  registerElementCoefficients("GaCl", Array[Double](-70553.0, 332.2718, 37.11052, -0.000746187, 1.1606512, 4.891346, -4.467591, 5.506236, 105.173, 3.696, 348.2))
  registerElementCoefficients("GaCl2", Array[Double](-241238.0, 443.2976, 57.745845, -0.002265112, 1.8755545, 3.66186, -9.356338, 15.88245, 140.626, 4.293, 465.0))
  registerElementCoefficients("GaCl3", Array[Double](-431573.0, 526.8113, 82.03355, -0.003486473, 2.6855923, 8.278878, -14.5678, 12.8899, 176.080, 5.034, 548.24))
  registerElementCoefficients("NH3", Array[Double](-45940.0, 231.1183, 20.52222, 0.000716251, 0.7677236, 244.6296, -251.69, 146.6947, 17.031, 3.0, 300.0))
  registerElementCoefficients("H2", Array[Double](0.0, 205.5368, 29.50487, 0.000168424, 0.86065612, -14.95312, 78.18955, -82.78981, 2.016, 2.93, 34.1))
  registerElementCoefficients("HCl", Array[Double](-92310.0, 243.9878, 23.15984, 0.001819985, 0.6147384, 51.16604, -36.89502, 9.174252, 36.461, 2.737, 167.1))
  registerElementCoefficients("N2", Array[Double](0.0, 242.8156, 21.47467, 0.001748786, 0.5910039, 81.08497, -103.6265, 71.30775, 28.0135, 3.798, 71.4))
  registerElementCoefficients("Al", Array[Double](0.0, 172.8289, 50.51806, -0.00411847, 1.476107, -458.1279, 2105.75, -4168.337, 26.9815))
  registerElementCoefficients("Ga", Array[Double](0.0, 125.9597, 26.03107, 0.001178297, 0.13976, -0.5698425, 0.04723008, 7.212525, 69.723))
  registerElementCoefficients("AlN", Array[Double](-319000.0, 123.1132, 44.98092, -0.00734504, 1.86107, 31.39626, -49.92139, 81.22038, 40.988))
  registerElementCoefficients("GaN", Array[Double](-114000.0, 160.2647, 52.86351, -0.00799055, 2.113389, 1.313428, -2.441129, 1.945731, 83.730))

  registerElementDensity("Al", 2690)
  registerElementDensity("Ga", 5900)
  registerElementDensity("AlN", 3200)
  registerElementDensity("GaN", 6150)

  kEvaluator += (1 -> new K(-1).plus(2, "Al").plus(2, "HCl").minus(2, "AlCl").minus(1, "H2").formula)
  kEvaluator += (2 -> new K(0).plus(1, "Al").plus(2, "HCl").minus(1, "AlCl2").minus(1, "H2").formula)
  kEvaluator += (3 -> new K(1).plus(2, "Al").plus(6, "HCl").minus(2, "AlCl3").minus(3, "H2").formula)
  kEvaluator += (4 -> new K(-1).plus(2, "Ga").plus(2, "HCl").minus(2, "GaCl").minus(1, "H2").formula)
  kEvaluator += (5 -> new K(0).plus(1, "Ga").plus(2, "HCl").minus(1, "GaCl2").minus(1, "H2").formula)
  kEvaluator += (6 -> new K(1).plus(2, "Ga").plus(6, "HCl").minus(2, "GaCl3").minus(3, "H2").formula)
  kEvaluator += (9 -> new K(-1).plus(1, "AlCl3").plus(1, "NH3").minus(1, "AlN").minus(3, "HCl").formula)
  kEvaluator += (10 -> new K(0).plus(1, "GaCl").plus(1, "NH3").minus(1, "GaN").minus(1, "HCl").minus(1, "H2").formula)

  private def registerElementCoefficients(element: String, params: Array[Double]) =
    coefficients += (element.toUpperCase() -> new ElementCoefficients(params))

  private def registerElementDensity(element: String, density: Double) =
    densities += (element.toUpperCase() -> density)

  def getElementCoefficients(element: String) = coefficients(element.toUpperCase)

  def getElementDensity(element: String): Double = densities(element.toUpperCase())

  private def getGEvaluator(element: String) = coefficients(element.toUpperCase).gCounter

  def getKEvaluator(number: Int) = kEvaluator(number)

  def getDEvaluator(element: String) = coefficients(element.toUpperCase).dCounter

  def getMolarValue(element: String) = getElementCoefficients(element).mu / getElementDensity(element)

  class ElementCoefficients(params: Array[Double]) {
    val h = params(0)
    val f = params.slice(1, 8)
    val mu = params(8)
    val sigma = if (params.length > 9) params(9) else Double.NaN
    val eps = if (params.length > 9) params(10) else Double.NaN
    val gCounter = (t: Double) => {
      val x = t / 1e4
      h - t * (f(0) + f(1) * math.log(x) + (f(2) / x + f(3)) / x + (f(4) + (f(5) + f(6) * x) * x) * x)
    }
    val dCounter = (t: Double) => {
      val n2 = getElementCoefficients("N2")
      val mySigma = (sigma + n2.sigma) / 2
      val myEps = math.sqrt(eps * n2.eps)
      val myMu = 2 * mu * n2.mu / (mu + n2.mu)
      2.628e-2 * math.pow(t, 1.5) / (1e5 * mySigma * 1.074 * math.pow(t / myEps, -0.1604) * math.sqrt(myMu))
    }
  }

  private class K(p: Double) {
    private val pPower = p
    private var gCoefficients: Map[String, Int] = Map()

    def plus(coef: Int, element: String): K = {
      if (gCoefficients.contains(element.toUpperCase))
        throw new RuntimeException("Element has been already added in formula")
      gCoefficients += (element -> coef)
      this
    }

    def minus(coef: Int, element: String): K = plus(-coef, element)

    val formula = (t: Double) => {
      val delta: Double = gCoefficients.toSeq.aggregate(0.0)((res, coef) => res + coef._2 * getGEvaluator(coef._1)(t), (a, b) => a + b)
      math.exp(-delta / 8.3144621 / t) * math.pow(1e5, pPower)
    }
  }


}



