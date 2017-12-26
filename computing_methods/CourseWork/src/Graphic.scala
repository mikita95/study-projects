import com.xeiam.xchart._

class Graphic(x: String, y: String) {
  private val chart: Chart = new ChartBuilder().width(800).height(600).build()
  chart.getStyleManager.setLegendPosition(StyleManager.LegendPosition.InsideNW)
  chart.setXAxisTitle(x)
  chart.setYAxisTitle(y)

  def addGraphic(points: List[Point], name: String): Graphic = {

    val d1 = (for (p <- points; a = p.x) yield a).toArray
    val d2 = (for (p <- points; a = p.y) yield a).toArray
    chart.addSeries(name, d1, d2)
    this
  }

  def show = new SwingWrapper(chart).displayChart()

  def saveBitmap(file: String) = BitmapEncoder.saveBitmap(chart, file, BitmapEncoder.BitmapFormat.PNG)


}
