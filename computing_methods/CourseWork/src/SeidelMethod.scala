class SeidelMethod(iterationsLimit: Int) extends LinearSystemsSolvingMethod {
  override def solve(s: LinearEquationSystem, epsilon: Double): (Vector, Int) = {
    val A = s.getA
    val b = s.getB
    val n = b.getDimension
    var x = Vector.zero(n)
    var nextX = x
    var iterations = 0
    do {
      x = nextX
      val temp = Array.ofDim[Double](A.getHeight)
      for (i <- 0 until n) {
        var sum = 0d
        for (j <- 0 until i) sum = sum + (-A(i, j)) * temp(j)
        for (j <- i + 1 until n) sum = sum + (-A(i, j)) * x(j)
        temp update(i, (sum + b(i)) / A(i, i))
      }
      nextX = new Vector(temp)
      iterations = iterations + 1
      if (nextX.hasNan || iterations > iterationsLimit) return null
    } while ((A * x - b).norm > epsilon * 0.1)
    if (!(A * x - b).norm.<=(epsilon * 0.1) && !Vector.distance(x, nextX).<=(epsilon * 0.1)) null
    else (nextX, iterations)
  }
}
