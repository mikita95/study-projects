class Matrix(h: Int, w: Int) {
  require(h > 0 && w > 0)
  private var values = Array.ofDim[Double](h, w)

  def this(a: Array[Array[Double]]) = {
    this(a.length, a(0).length)
    values = a.clone()
  }

  def getValues: Array[Array[Double]] = values.clone()

  def getHeight: Int = values.length

  def getWidth: Int = values(0).length

  def set(i: Int, j: Int, value: Double) = values(i) update(j, value)

  def apply(i: Int, j: Int) = get(i, j)

  def get(i: Int, j: Int): Double = values(i)(j)

  def norm = {
    var norm = 0d
    for (i <- 0 until getHeight) {
      var sum = 0d
      for (j <- 0 until getWidth) sum = sum + math.abs(get(i, j))
      if (sum > norm) norm = sum
    }
    norm
  }

  def mop(that: Matrix)(op: (Double, Double) => Double): Matrix = new Matrix((this.values zip that.values).map(t => (t._1 zip t._2).map(s => op(s._1, s._2))))

  def +(matrix: Matrix): Matrix = {
    assert(!(getHeight != matrix.getHeight || getWidth != matrix.getWidth))
    this.mop(matrix)(_ + _)
  }

  def *(matrix: Matrix): Matrix = {
    assert(getWidth == matrix.getHeight)
    val result = Array.ofDim[Double](getHeight, getWidth)
    for (i <- 0 until getHeight; j <- 0 until getWidth; k <- 0 until getWidth)
      result(i).update(j, result(i)(j) + get(i, k) * matrix(k, j))
    new Matrix(result)
  }

  def -(matrix: Matrix): Matrix = {
    assert(!(getHeight != matrix.getHeight || getWidth != matrix.getWidth))
    this.mop(matrix)(_ - _)
  }

  def *(v: Double): Matrix = {
    new Matrix(values.map(a => a.map(x => x * v)))
  }

  def *(v: Vector): Vector = {
    assert(getWidth == v.getDimension)
    val result = new Vector(getHeight)
    for (i <- 0 until getHeight; j <- 0 until getWidth) {
      result.set(i, result(i) + get(i, j) * v(j))
    }
    result
  }

  def croutLUDecomposition: (Matrix, Matrix) = {
    val n = getHeight
    val U = new Matrix(n, n)
    val L = new Matrix(n, n)
    for (i <- 0 until n) U.set(i, i, 1)
    for (j <- 0 until n) {
      for (i <- j until n) {
        var sum = 0d
        for (k <- 0 until j) sum = sum + L(i, k) * U(k, j)
        L.set(i, j, get(i, j) - sum)
      }
      for (i <- j until n) {
        var sum = 0d
        for (k <- 0 until j) sum = sum + L(j, k) * U(k, i)
        if (L(j, j) == 0) return null
        U.set(j, i, (get(j, i) - sum) / L(j, j))
      }
    }
    (U, L)
  }

  def determinant: Double = {
    val ul = croutLUDecomposition
    if (ul == null) return 0d
    var result = ul._1(0, 0) * ul._2(0, 0)
    for (i <- 1 until getHeight) result *= ul._1(i, i) * ul._2(i, i)
    result
  }

  def determinant1: Double = {
    if (getHeight == 1) get(0, 0)
    if (getHeight == 2) return get(0, 0) * get(1, 1) - get(1, 0) * get(0, 1)
    var result = 0d
    for (i <- 0 until getHeight) {
      result = result +
        (if (i % 2 == 0) 1 else  - 1) * get(i, 0) * minor(i, 0).determinant1
    }
    result
  }

  def reversed: Matrix = {
    val det: Double = determinant1
    //if (det.abs < 1e-18)
      //return null

    new Matrix(Array.tabulate(getHeight, getWidth)((j, i) => (if ((i + j) % 2 == 0) 1 else -1) * minor(i, j).determinant1 / det))
  }

  def minor(a: Int, b: Int): Matrix =
    new Matrix(Array.tabulate(getHeight - 1, getWidth - 1)((i, j) => get(if (i < a) i else i + 1, if (j < b) j else j + 1)))

  def conditionality = norm * reversed.norm

  def equals(matrix: Matrix, epsilon: Double): Boolean = {
    if (getHeight != matrix.getHeight || getWidth != matrix.getWidth) return false
    values.indices.forall((i) => values(i).indices.forall(j => (get(i, j) - matrix(i, j)).abs < epsilon))
  }

  def transpose: Matrix = new Matrix(values.transpose)

  def getDiagonal: Array[Double] = (for (i <- 0 until getHeight; x = get(i, i)) yield x).toArray

  def getRow(i: Int): Vector = new Vector(values(i).clone())

  def excludeVariableForward(i: Int): Matrix = {
    assert(!(i < 0 || i >= values(0).length))
    val result = getValues
    val row: Vector = getRow(i)
    for (k <- (i + 1 until values.length).reverse) {
      result update(k, (getRow(k) + (row * (-get(k, i) / get(i, i)))).getValues)
    }
    new Matrix(result)
  }

  def excludeVariableBackward(i: Int): Matrix = {
    assert(!(i < 0 || i >= values(0).length))
    val result = getValues
    var row: Vector = new Vector(getRow(i).getValues)
    row = row * (1d / row(i))
    for (j <- (0 until i).reverse) {
      result update(j, (getRow(j) + row * (-get(j, i))).getValues)
    }
    new Matrix(result)
  }

  def spectralRadiusLessOne(epsilon: Double): Double = {
    var result = 0d
    var nextResult = 0d
    val A = new Matrix(getValues)
    result = A.norm
    nextResult = result
    var Ak = A
    var i = 1
    val maxIterations = 10000
    do {
      result = nextResult
      Ak = Ak * A
      i = i + 1
      nextResult = math.pow(Ak.norm, 1d / i)
    } while (math.abs(result - nextResult) > epsilon && i < maxIterations)
    nextResult
  }

  override def toString = {
    val result: StringBuilder = new StringBuilder
    for (i <- 0 until getHeight) {
      for (j <- 0 until getWidth)
        result.append("%3.3f  " format get(i, j))
      result.append("\n")
    }
    result.toString()
  }
}

object Matrix {
  def zero(h: Int, w: Int): Matrix = new Matrix(h, w)

  def zero(n: Int): Matrix = zero(n, n)

  def byDiagonal(trace: Array[Double]): Matrix = new Matrix(Array.tabulate[Double](trace.length, trace.length)((i, j) => if (i == j) trace(i) else 0d))

  def unit(d: Int): Matrix = new Matrix(Array.tabulate[Double](d, d)((i, j) => if (i == j) 1d else 0d))

  def getExtended(a: Matrix, b: Vector): Matrix = new Matrix(Array.tabulate[Double](a.getHeight, a.getWidth + 1)((i, j) => if (j < a.getWidth) a(i, j) else b(i)))

  def parse(s: String): Matrix = {
    var split = scala.Vector[Int]()
    var balance = 0
    for (i <- 0 until s.length)
      s(i) match {
        case '{' => balance = balance + 1
        case '}' => balance = balance - 1
        case ',' => if (balance == 0) split = split :+ i
        case _ =>
      }
    split = split :+ s.length
    new Matrix((for {i <- split.indices
                     line = s.substring((if (i == 0) 0 else split(i - 1)) + 1, split(i) - 1).trim.replaceAll("[\\}\\{,]", " ").split("\\s+")
                     r = Array.tabulate[Double](line.length - 1)(j => line(j + 1).toDouble)
    } yield r).toArray)
  }
}
