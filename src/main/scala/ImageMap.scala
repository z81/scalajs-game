package game

class ImageMap(src: String, size: Int) {
  val image = new Image(src)

  def getTexture(x: Int, y: Int): ImageRegion = {
    new ImageRegion(image, x * size,  y * size, size, size)
  }

  def isReady: Boolean = image.isReady
}
