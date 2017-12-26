object Systems {
  def system1(init: Vector, epsilon: Double, maxIteration: Int)(t: Double) = {
    val K1 = Coefficients.getKEvaluator(1)(t)
    val K2 = Coefficients.getKEvaluator(2)(t)
    val K3 = Coefficients.getKEvaluator(3)(t)
    val elementNames = List("AlCl", "AlCl2", "AlCl3", "H2", "HCl")
    val pg = List(0d, 0d, 0d, 0d, 10000d)
    val mf = (vars: Vector, k: Int) => Coefficients.getDEvaluator(elementNames(k))(t) * (pg(k) - vars(k))
    val functions: List[Vector => Double] = List(
      vars => math.pow(vars(4), 2) - K1 * math.pow(vars(0), 2) * vars(3),
      vars => math.pow(vars(4), 2) - K2 * vars(1) * vars(3),
      vars => math.pow(vars(4), 6) - K3 * math.pow(vars(2), 2) * math.pow(vars(3), 3),
      vars => mf(vars, 4) + 2 * mf(vars, 3),
      vars => mf(vars, 0) + 2 * mf(vars, 1) + 3 * mf(vars, 2) + mf(vars, 4)
    )
    val derivatives: List[List[Vector => Double]] = List(
      List(
        vars => -2 * K1 * vars(0) * vars(3),
        vars => 0d,
        vars => 0d,
        vars => -K1 * math.pow(vars(0), 2),
        vars => 2 * vars(4)
      ),
      List(
        vars => 0d,
        vars => -K2 * vars(3),
        vars => 0d,
        vars => -K2 * vars(1),
        vars => 2 * vars(4)
      ),
      List(
        vars => 0d,
        vars => 0d,
        vars => -2 * K3 * vars(2) * math.pow(vars(3), 3),
        vars => -3 * K3 * math.pow(vars(2), 2) * math.pow(vars(3), 2),
        vars => 6 * math.pow(vars(4), 5)
      ),
      List(
        vars => 0d,
        vars => 0d,
        vars => 0d,
        vars => -2 * Coefficients.getDEvaluator("H2")(t),
        vars => -Coefficients.getDEvaluator("HCl")(t)
      ),
      List(
        vars => -Coefficients.getDEvaluator("AlCl")(t),
        vars => -2 * Coefficients.getDEvaluator("AlCl2")(t),
        vars => -3 * Coefficients.getDEvaluator("AlCl3")(t),
        vars => 0d,
        vars => -Coefficients.getDEvaluator("HCl")(t)
      )
    )
    new Solver(functions, derivatives).solve(init, epsilon, maxIteration)
  }

  def system2(init: Vector, epsilon: Double, maxIteration: Int)(t: Double) = {
    val K4 = Coefficients.getKEvaluator(4)(t)
    val K5 = Coefficients.getKEvaluator(5)(t)
    val K6 = Coefficients.getKEvaluator(6)(t)
    val elementNames = List("GaCl", "GaCl2", "GaCl3", "H2", "HCl")
    val pg = List(0d, 0d, 0d, 0d, 10000d)
    val mf = (vars: Vector, k: Int) => Coefficients.getDEvaluator(elementNames(k))(t) * (pg(k) - vars(k))
    val functions: List[Vector => Double] = List(
      vars => math.pow(vars(4), 2) - K4 * math.pow(vars(0), 2) * vars(3),
      vars => math.pow(vars(4), 2) - K5 * vars(1) * vars(3),
      vars => math.pow(vars(4), 6) - K6 * math.pow(vars(2), 2) * math.pow(vars(3), 3),
      vars => mf(vars, 4) + 2 * mf(vars, 3),
      vars => mf(vars, 0) + 2 * mf(vars, 1) + 3 * mf(vars, 2) + mf(vars, 4)
    )
    val derivatives: List[List[Vector => Double]] = List(
      List(
        vars => -2 * K4 * vars(0) * vars(3),
        vars => 0d,
        vars => 0d,
        vars => -K4 * math.pow(vars(0), 2),
        vars => 2 * vars(4)
      ),
      List(
        vars => 0d,
        vars => -K5 * vars(3),
        vars => 0d,
        vars => -K5 * vars(1),
        vars => 2 * vars(4)
      ),
      List(
        vars => 0d,
        vars => 0d,
        vars => -2 * K6 * vars(2) * math.pow(vars(3), 3),
        vars => -3 * K6 * math.pow(vars(2), 2) * math.pow(vars(3), 2),
        vars => 6 * math.pow(vars(4), 5)
      ),
      List(
        vars => 0d,
        vars => 0d,
        vars => 0d,
        vars => -2 * Coefficients.getDEvaluator("H2")(t),
        vars => -Coefficients.getDEvaluator("HCl")(t)
      ),
      List(
        vars => -Coefficients.getDEvaluator("GaCl")(t),
        vars => -2 * Coefficients.getDEvaluator("GaCl2")(t),
        vars => -3 * Coefficients.getDEvaluator("GaCl3")(t),
        vars => 0d,
        vars => -Coefficients.getDEvaluator("HCl")(t)
      )
    )
    new Solver(functions, derivatives).solve(init, epsilon, maxIteration)
  }

  def system3(H2: Double, init: Vector, epsilon: Double, maxIteration: Int)(xg: Double) = {
    val t = 1100d + 273d
    val K9 = Coefficients.getKEvaluator(9)(t)
    val K10 = Coefficients.getKEvaluator(10)(t)
    val elementNames = List("AlCl3", "GaCl", "NH3", "HCl", "H2")
    val pg = List(xg * 30, (1 - xg) * 30, 1500d, 0d, H2 * 98470)
    val mf = (vars: Vector, k: Int) => Coefficients.getDEvaluator(elementNames(k))(t) * (pg(k) - vars(k))
    val functions: List[Vector => Double] = List(
      vars => vars(0) * vars(2) - K9 * vars(5) * math.pow(vars(3), 3),
      vars => vars(1) * vars(2) - K10 * (1 - vars(5)) * vars(3) * vars(4),
      vars => mf(vars, 3) + 2 * mf(vars, 4) + 3 * mf(vars, 2),
      vars => 3 * mf(vars, 0) + mf(vars, 1) + mf(vars, 3),
      vars => mf(vars, 0) + mf(vars, 1) - mf(vars, 2),
      vars => mf(vars, 0) * (1 - vars(5)) - mf(vars, 1) * vars(5)
    )
    val derivatives: List[List[Vector => Double]] = List(
      List(
        vars => vars(2),
        vars => 0d,
        vars => vars(0),
        vars => -3 * K9 * vars(5) * math.pow(vars(3), 2),
        vars => 0d,
        vars => -K9 * math.pow(vars(3), 3)
      ),
      List(
        vars => 0d,
        vars => vars(2),
        vars => vars(1),
        vars => -K10 * (1 - vars(5)) * vars(4),
        vars => -K10 * (1 - vars(5)) * vars(3),
        vars => K10 * vars(3) * vars(4)
      ),
      List(
        vars => 0d,
        vars => 0d,
        vars => -3 * Coefficients.getDEvaluator("NH3")(t),
        vars => -Coefficients.getDEvaluator("HCl")(t),
        vars => -2 * Coefficients.getDEvaluator("H2")(t),
        vars => 0d
      ),
      List(
        vars => -3 * Coefficients.getDEvaluator("AlCl3")(t),
        vars => -Coefficients.getDEvaluator("GaCl")(t),
        vars => 0d,
        vars => -2 * Coefficients.getDEvaluator("HCl")(t),
        vars => 0d,
        vars => 0d
      ),
      List(
        vars => -Coefficients.getDEvaluator("AlCl3")(t),
        vars => -Coefficients.getDEvaluator("GaCl")(t),
        vars => Coefficients.getDEvaluator("NH3")(t),
        vars => 0d,
        vars => 0d,
        vars => 0d
      ),
      List(
        vars => -Coefficients.getDEvaluator("AlCl3")(t) * (1 - vars(5)),
        vars => Coefficients.getDEvaluator("GaCl")(t) * vars(5),
        vars => 0d,
        vars => 0d,
        vars => 0d,
        vars => -mf(vars, 0) - mf(vars, 1)
      )
    )
    new Solver(functions, derivatives).solve(init, epsilon, maxIteration)
  }
}
