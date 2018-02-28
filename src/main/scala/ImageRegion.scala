package game

import org.scalajs.dom.CanvasRenderingContext2D

class ImageRegion(img: Image, x1: Int, y1: Int, width: Int, height: Int) {

  def draw(ctx: CanvasRenderingContext2D, x: Int, y: Int) {
    ctx.drawImage(img.element, x1, y1, width, height, x, y, width, height)
  }
}
