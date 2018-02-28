package game

import org.scalajs.dom.CanvasRenderingContext2D

import scala.scalajs.js

class Player(xPos: Int, yPos: Int, maxVisibleRadius: Int, name: String, texture: ImageRegion) {
  //val Direactions = Map("Right" -> 0, "Left" -> 1)
  var x = xPos
  var y = yPos
  var hp = 100
  var armor = 100
  var stamina = 100
  var visibleRadius = maxVisibleRadius
  var username = name
  var direction = "right"

  js.timers.setInterval(100) {
    if (stamina < 100) {
      stamina += 1
    }
  }

  def move(cx: Int, cy: Int) {
    if (stamina < 20) return

    x += cx
    y += cy

    stamina = Math.max(stamina - 20, 0)
  }


  def render(ctx: CanvasRenderingContext2D, gridSize: Int): Unit = {

    val textSize = ctx.measureText(username)
    var textXPosition = gridSize * x - textSize.width.toInt / 2 + gridSize / 2
    ctx.fillStyle = "rgb(230, 230, 230)"
    ctx.fillText(username, textXPosition - 1 , gridSize * y - gridSize / 4 - 1)
    ctx.fillText(username, textXPosition + 1 , gridSize * y - gridSize / 4 + 1)
    ctx.fillStyle = "black"
    ctx.fillText(username, textXPosition , gridSize * y - gridSize / 4)

//    ctx.save
//    ctx.scale(1, -1)
    texture.draw(ctx, gridSize * x, gridSize * y)
    //ctx.restore()
  }
}
