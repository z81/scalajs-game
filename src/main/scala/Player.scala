package game

import org.scalajs.dom.CanvasRenderingContext2D

import scala.scalajs.js

class Player(xPos: Int, yPos: Int, maxVisibleRadius: Int, name: String, skinName: String) {
  var x: Int = xPos
  var y: Int = yPos
  var hp = 100
  var armor = 100
  var stamina = 100
  var damage = 100
  var visibleRadius: Int = maxVisibleRadius
  var username: String = name
  var direction = "right"
  var texturePack: TexturePack = null

  js.timers.setInterval(100) {
    if (stamina < 100) {
      stamina += 1
    }
  }

  def move(cx: Int, cy: Int) {
    if (stamina < 3) return

    x += cx
    y += cy

    stamina = Math.max(stamina - 3, 0)
  }

  def isMonster: Boolean = {
    skinName == "monster"
  }


  def render(ctx: CanvasRenderingContext2D, gridSize: Int): Unit = {

    val textSize = ctx.measureText(username)
    var textXPosition = gridSize * x - textSize.width.toInt / 2 + gridSize / 2
    ctx.fillStyle = "rgb(230, 230, 230)"
    ctx.fillText(username, textXPosition - 1 , gridSize * y - gridSize / 4 - 1)
    ctx.fillText(username, textXPosition + 1 , gridSize * y - gridSize / 4 + 1)
    ctx.fillStyle = if (skinName == "monster") "red" else "black"
    ctx.fillText(username, textXPosition , gridSize * y - gridSize / 4)

//    ctx.save
//    ctx.scale(1, -1)
    texturePack.get(if (hp > 0) skinName else "die").draw(ctx, gridSize * x, gridSize * y)
    //ctx.restore()
  }
}
