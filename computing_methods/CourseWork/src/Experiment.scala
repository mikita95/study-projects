class Experiment(m: List[Point]) {
  private var measures = m

  def this(f: Double => Double, a: Double, b: Double, step: Double) = {
    this(List())
    for (x <- a until b * (1 + 1e-9) by step) {
      var y = 0d
      try {
        y = f(x)
      } catch {
        case e: Exception =>
          System.err.println(x)
          y = Double.NaN
      }
      measures = measures ::: List(new Point(x, y))
    }
  }

  def get = measures

  def mapX(f: Double => Double) = new Experiment(measures.map(point => new Point(f(point.x), point.y)))

  def mapY(f: Double => Double) = new Experiment(measures.map(point => new Point(point.x, f(point.y))))

}

object Experiment {

  class ExperimentSeries(a: Double, b: Double, step: Double) {
    private var x: List[Double => Double] = List()
    private var y: List[Double => Double] = List()

    def create(f: Double => Double) = {
      var e: Experiment = new Experiment(f, a, b, step)
      for (map <- x) e = e.mapX(map)
      for (map <- y) e = e.mapY(map)
      e
    }

    def mapX(f: Double => Double) = {
      x = x ::: List(f)
      this
    }

    def mapY(f: Double => Double) = {
      y = y ::: List(f)
      this
    }
  }

}


