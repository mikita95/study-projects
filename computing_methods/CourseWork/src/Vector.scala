class Vector(d: Int) {
  require(d > 0)
  private var values = Array.ofDim[Double](d)

  def this(v: Double*) = {
    this(v.length)
    this.values = v.toArray
  }

  def this(v: Array[Double]) = {
    this(v.length)
    this.values = v.clone()
  }

  def apply(i: Int) = get(i)

  def getValues = values.clone()

  def fill(v: Double) = values = Array.fill[Double](values.length)(v)

  def getDimension = values.length

  def get(i: Int) = values(i)

  def set(i: Int, v: Double) = values update(i, v)

  def +(vector: Vector): Vector = new Vector(Array.tabulate[Double](getDimension)(i => get(i) + vector.get(i)))

  def *(a: Double): Vector = new Vector(Array.tabulate[Double](getDimension)(i => a * get(i)))

  def -(vector: Vector): Vector = this + vector * (-1d)

  def *(vector: Vector): Double = {
    assert(getDimension == vector.getDimension)
    var sum = 0d
    for (i <- 0 until getDimension) sum = sum + get(i) * vector.get(i)
    sum
  }

  def equals(vector: Vector, epsilon: Double): Boolean = {
    if (getDimension != vector.getDimension) return false
    values.indices.forall((i) => math.abs(get(i) - vector.get(i)) < epsilon)
  }

  def norm = Vector.distance(this, Vector.zero(getDimension))

  def hasNan: Boolean = values.exists(x => x.isNaN)

  override def toString: String = "(" + values.mkString(", ") + ")"
}

object Vector {
  def distance(a: Vector, b: Vector): Double = {
    assert(a.getDimension == b.getDimension)
    var sum = 0d
    for (i <- 0 until a.getDimension) sum = sum + (b(i) - a(i)) * (b(i) - a(i))
    math.sqrt(sum)
  }

  def zero(d: Int) = new Vector(d)
}
