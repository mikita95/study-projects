class JacobiMethod(iterationLimit: Int) extends LinearSystemsSolvingMethod {
  override def solve(s: LinearEquationSystem, epsilon: Double): (Vector, Int) = {
    val dReversed = Matrix.byDiagonal(s.getA.getDiagonal).reversed
    if (dReversed == null) return null
    val B = Matrix.unit(s.getA.getHeight) - dReversed * s.getA
    val g = dReversed * s.getB
    val q = B.norm
    var x = Vector.zero(s.getB.getDimension)
    var nextX = x
    var iterations = 0
    do {
      x = nextX
      nextX = B * x + g
      iterations = iterations + 1
      if (nextX.hasNan || iterations > iterationLimit) return null
      //} while (!x.equals(nextX, epsilon * (1 - q)))
    } while ((x - nextX).norm > (1 - q) / q * epsilon)
    (nextX, iterations)
  }
}
