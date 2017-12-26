class LinearEquationSystem(a: Matrix, v: Vector) {
  require(a.getHeight == v.getDimension)
  private var A = a
  private var b = v

  def this(a: Matrix) = this(a, Vector.zero(a.getHeight))

  def getA = A

  def getB = b

  def setA(a: Matrix) = A = a

  def setB(v: Vector) = b = v

  def size = b.getDimension
}
