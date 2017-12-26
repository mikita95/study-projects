/**
  * Created by nikita on 07.05.16.
  */
class CachingWrapper[K, V](foo: K => V) extends ((K) => V) {
  private val f: K => V = foo
  private var cache: Map[K, V] = Map()

  override def apply(key: K): V = {
    if (!cache.contains(key)) {
      val newValue = f(key)
      if (newValue != null)
        cache += (key -> newValue)
      return newValue
    }
    cache(key)
  }
}
