trait LinearSystemsSolvingMethod {
  def solve(system: LinearEquationSystem, epsilon: Double): (Vector, Int)
}
