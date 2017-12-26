class GaussMethod extends LinearSystemsSolvingMethod {
  override def solve(s: LinearEquationSystem, epsilon: Double): (Vector, Int) = {
    var a: Matrix = Matrix.getExtended(s.getA, s.getB)
    val n = s.getA.getHeight
    for (i <- 0 until n) a = a.excludeVariableForward(i)
    (new Vector((0 until n).reverse.map(i => {
      val r = a(i, n) / a(i, i)
      a = a.excludeVariableBackward(i)
      r
    }).reverse.toArray), 0)
  }
}
