class GradientMethod(iterationsLimits: Int) extends LinearSystemsSolvingMethod {
  override def solve(s: LinearEquationSystem, epsilon: Double): (Vector, Int) = {
    val A = s.getA
    val b = s.getB
    val at = A.transpose
    var x = new Vector(Array.tabulate[Double](b.getDimension)(i => -b(i) / A(i, i)))
    var rp = A * x - b
    var iterations = 0
    while (rp.norm > epsilon) {
      val w = A * at * rp
      val mu = rp * w / (w.norm * w.norm)
      x = x - (at * rp * mu)
      iterations = iterations + 1
      if (iterations > iterationsLimits || x.hasNan) return null
      rp = A * x - b
    }
    (x, iterations)
  }
}
