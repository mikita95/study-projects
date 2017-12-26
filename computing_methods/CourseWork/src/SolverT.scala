object SolverT {
  val ITERATIONS_LIMIT = 100000
  val EPSILON = 1e-6
  val solvers = Map(
    "Gauss method" -> new GaussMethod,
    "Jacobi method" -> new JacobiMethod(ITERATIONS_LIMIT),
    "Seidel method" -> new SeidelMethod(ITERATIONS_LIMIT),
    "Seidel relaxation method" -> new SeidelRelaxationMet(ITERATIONS_LIMIT),
    "Gradient method" -> new GradientMethod(ITERATIONS_LIMIT))

  def main(args: Array[String]): Unit = {
    testBadConditionality()
    testAverageConditionality()
    testGoodConditionality()
  }

  def runTest(a: Matrix, b: Vector, answer: Vector) = {
    println("\n-------------------------------")
    println("A = \n" + a)
    println("b = " + b)
    println("Cond A = " + a.conditionality)
    println("Spectral radius A = " + a.spectralRadiusLessOne(EPSILON))
    //println(a.norm)
    println
    for (name <- solvers.keys) {
      print(name + ": ")
      val solver = solvers(name)
      val system1 = new LinearEquationSystem(a, b)
      val solution = solver.solve(system1, EPSILON)
      if (solution == null) println("no convergence")
      else {
        assert(solution._1.equals(answer, EPSILON))
        println("ok: " + solution._2 + " sol: " + solution._1)
      }
    }
  }

  def testAverageConditionality() = {
    println("Testing average conditionality")
    val a1: Matrix = Matrix.parse(" {12, 11, 11, 11}, " + "{12, 13, 12, 11}, " + "{17, 18, 19, 18}, " + "{25, 23, 25, 26}")
    val b1: Vector = new Vector(1, 2, 3, 4)
    val answer1: Vector = new Vector(-6d / 5, 7d / 5, -9d / 5, 9d / 5)
    runTest(a1, b1, answer1)
    val a2: Matrix = Matrix.parse(" {-5, 2, 5, 5}, " + "{5, -7, 5, -4}, " + "{-5, 4, -16, 16}, " + "{23, 2, -24, 26}")
    val b2: Vector = new Vector(-100, -5, 10, 15)
    val answer2: Vector = new Vector(1085d / 2323, -9025d / 9292, -93655d / 9292, -84235d / 9292)
    runTest(a2, b2, answer2)
    val a3: Matrix = Matrix.parse(" {-8, 2, 5, 5}, " + "{5, -9, 5, -4}, " + "{-5, 4, -20, 16}, " + "{23, 2, -24, 40}")
    val b3: Vector = new Vector(-100, -5, 10, 15)
    val answer3: Vector = new Vector(166590d / 32987, 176795d / 65974, -425895d / 65974, -431215d / 65974)
    runTest(a3, b3, answer3)
  }

  def testGoodConditionality() = {
    println("Testing good conditionality")
    val a1: Matrix = Matrix.parse(" {99, 2, 1, 1, 0, 0, 0, 0, 0, 0}, " + "{2, 99, 2, 1, 1, 0, 0, 0, 0, 0}, " + "{1, 2, 99, 2, 1, 1, 0, 0, 0, 0}, " + "{1, 1, 2, 99, 2, 1, 1, 0, 0, 0}, " + "{0, 1, 1, 2, 99, 2, 1, 1, 0, 0}, " + "{0, 0, 1, 1, 2, 99, 2, 1, 1, 0}, " + "{0, 0, 0, 1, 1, 2, 99, 2, 1, 1}, " + "{0, 0, 0, 0, 1, 1, 2, 99, 2, 1}, " + "{0, 0, 0, 0, 0, 1, 1, 2, 99, 2}, " + "{0, 0, 0, 0, 0, 0, 1, 1, 2, 99}")
    val b1: Vector = new Vector(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val answer1: Vector = new Vector(839092143287068d / 92571266157142257d, 1722084794688938d / 92571266157142257d, 2595909981697204d / 92571266157142257d, 3461064400647445d / 92571266157142257d, 4325068989463663d / 92571266157142257d, 5188250759980078d / 92571266157142257d, 6048303324194155d / 92571266157142257d, 7009572406973305d / 92571266157142257d, 7977480288192230d / 92571266157142257d, 9057573992564350d / 92571266157142257d)
    runTest(a1, b1, answer1)
  }

  def testBadConditionality() = {
    println("Testing bad conditionality")
    val a1: Matrix = Matrix.parse(" {0.5, 0.33, 0.25, 0.2, 0.15, 0.14, 0.125, 0.11, 0.1, 0.09}, " + "{0.33, 0.25, 0.2, 0.15, 0.14, 0.125, 0.11, 0.1, 0.09, 0.08}, " + "{0.25, 0.2, 0.15, 0.14, 0.125, 0.11, 0.1, 0.09, 0.08, 0.08}, " + "{0.2, 0.15, 0.14, 0.125, 0.11, 0.1, 0.09, 0.08, 0.08, 0.07}, " + "{0.15, 0.14, 0.125, 0.11, 0.1, 0.09, 0.08, 0.08, 0.07, 0.07}, " + "{0.14, 0.125, 0.11, 0.1, 0.09, 0.08, 0.08, 0.07, 0.07, 0.06}, " + "{0.125, 0.11, 0.1, 0.09, 0.08, 0.08, 0.07, 0.07, 0.06, 0.06}, " + "{0.11, 0.1, 0.09, 0.08, 0.08, 0.07, 0.07, 0.06, 0.06, 0.06}, " + "{0.1, 0.09, 0.08, 0.08, 0.07, 0.07, 0.06, 0.06, 0.06, 0.05}, " + "{0.09, 0.08, 0.08, 0.07, 0.07, 0.06, 0.06, 0.06, 0.05, 0.05}")
    val b1: Vector = new Vector(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val answer: Vector = new Vector(1268800d / 8173, -850800d / 8173, -4422200d / 24519, -2493400d / 8173, -178000d / 24519, -8647400d / 24519, -4674200d / 24519, 14940700d / 24519, 12421700d / 24519, 5568500d / 24519)
    runTest(a1, b1, answer)
  }

}
