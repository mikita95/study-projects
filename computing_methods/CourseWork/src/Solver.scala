class Solver(f: List[Vector => Double], d: List[List[Vector => Double]]) {
  private val functions = f
  private val derivatives = d

  def solve(init: Vector, epsilon: Double, max: Int): Vector = {
    var prev: Vector = new Vector(init.getValues: _*)
    val result: Vector = new Vector(init.getValues: _*)
    try {
      for (i <- 0 to max) {
        var matrix = new Matrix(f.length, f.length)
        for (j <- f.indices; k <- f.indices) matrix.set(j, k, derivatives(j)(k)(result))
        matrix = matrix.reversed
        var v: Vector = new Vector(result.getDimension)
        functions.indices.foreach(j => v.set(j, functions(j)(result)))
        v = matrix * v

        for (j <- 0 until result.getDimension) result.set(j, result.get(j) - v.get(j))
        if (Vector.distance(result, prev) < epsilon) {
          //System.err.println(result)
          return result
        }
        prev = new Vector(result.getValues: _*)

      }
    } catch {
      case e: Exception => e.printStackTrace()
}
System.err.println(result)
result
}
}
