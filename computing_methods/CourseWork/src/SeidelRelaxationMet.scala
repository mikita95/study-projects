import scala.util.control.Breaks._

class SeidelRelaxationMet(iterationsLimit: Int) extends LinearSystemsSolvingMethod {
 // private var w = 1.8

  override def solve(s: LinearEquationSystem, epsilon: Double): (Vector, Int) = {

    // if (n <= 10) {
    // only for small matrices
   /* var H = new Matrix(Array.tabulate[Double](n, n)((i, j) =>
      if (i != j) -A(i, j) / A(i, i)
      else 0d))
    var Hk = Matrix.unit(n)
    var i = 1
    var spectralNumber = 0d
    do {
      Hk = H
      H = H * H
      i = i + 1
      spectralNumber = math.pow(H.norm, 1d / i)
    } while (!spectralNumber.isInfinity && math.abs(spectralNumber - math.pow(Hk.norm, 1d / i)) >= epsilon && i < 10000)
    //for (i <- 1 to 1000) Hk = Hk * H

    val optimal = 2d / (1d + math.sqrt(1d - spectralNumber * spectralNumber))
    if (optimal >= 1e-6 && optimal <= 1.8) w = optimal
    if (spectralNumber.isInfinity) w = 1.92
    //}
*/
    var iterations = 0
    var w = 1.8
    while (w > 1e-6) {
      val A = s.getA
      val b = s.getB
      val n = b.getDimension
      //System.err.println(w)
      var x = Vector.zero(n)
      var nextX = x
      var flag = false

    //  breakable {
        do {
          x = nextX
          val temp = Array.ofDim[Double](A.getHeight)
          for (i <- 0 until n) {
            var sum = 0d
            for (j <- 0 until i) sum = sum + (-A(i, j)) * temp(j)
            for (j <- i + 1 until n) sum = sum + (-A(i, j)) * x(j)
            temp update(i, (sum + b(i)) / A(i, i))
          }
          nextX = new Vector(temp) * w
          nextX = nextX + (x * (1 - w))
          iterations = iterations + 1
          if (nextX.hasNan || iterations > iterationsLimit) {
            w = w / 1.25
            flag = true
          }
        } while (!flag && (A * x - b).norm > epsilon * 0.1)
     // }
      if (!(nextX.hasNan || iterations > iterationsLimit)) {
        if ((A * x - b).norm > (epsilon * 0.1) || Vector.distance(x, nextX) > (epsilon * 0.1))
          w = w / 1.25
        else {
         // System.err.print(w)
          return (nextX, iterations)
        }
      }
    }
    null
  }
}
