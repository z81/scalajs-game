package game

import org.scalajs.dom._
import scala.scalajs.js
import org.scalajs.dom.ext.KeyCode
import scala.math._

class Renderer {
  val gridSize = 16

  val canvas = document.createElement("canvas").asInstanceOf[html.Canvas]
  val ctx = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]
  ctx.scale(2, 2)

  canvas.width = window.innerWidth.toInt
  canvas.height = window.innerHeight.toInt
  document.body.appendChild(canvas)

  val mainTextures = new ImageMap("assets/images/pack1.png", gridSize)
  val grass = mainTextures getTexture(3, 0)
  val brick = mainTextures getTexture(6, 0)
  val playerTexture = mainTextures getTexture(0, 17)

  var player = new Player(5, 5)


  val map = List(
    List(brick, brick, brick, brick, brick, brick, brick, brick, brick, brick, brick, brick, brick, brick, brick, brick, brick, brick),
    List(brick, grass, grass, grass, grass, grass, grass, grass, brick, grass, grass, grass, grass, grass, grass, grass, grass, brick),
    List(brick, grass, grass, grass, grass, grass, grass, grass, brick, grass, grass, grass, grass, grass, grass, grass, grass, brick),
    List(brick, grass, grass, grass, grass, grass, grass, grass, brick, grass, grass, grass, grass, grass, grass, grass, grass, brick),
    List(brick, grass, grass, grass, grass, grass, grass, grass, brick, grass, grass, grass, brick, grass, grass, grass, grass, brick),
    List(brick, brick, brick, grass, grass, grass, grass, grass, brick, grass, grass, grass, brick, grass, grass, grass, grass, brick),
    List(brick, grass, brick, brick, grass, grass, grass, grass, brick, grass, grass, grass, brick, brick, brick, brick, brick, brick),
    List(brick, grass, grass, brick, grass, grass, brick, brick, brick, brick, grass, grass, brick, grass, grass, grass, grass, brick),
    List(brick, grass, grass, brick, brick, grass, grass, grass, grass, grass, grass, grass, brick, grass, grass, grass, grass, brick),
    List(brick, grass, grass, grass, brick, grass, grass, grass, grass, grass, grass, grass, brick, grass, grass, grass, grass, brick),
    List(brick, grass, grass, grass, brick, grass, grass, grass, grass, grass, grass, grass, brick, grass, grass, grass, grass, brick),
    List(brick, grass, grass, grass, brick, grass, grass, grass, grass, grass, grass, grass, grass, grass, grass, grass, grass, brick),
    List(brick, grass, grass, grass, grass, grass, grass, grass, grass, grass, grass, grass, grass, grass, grass, grass, grass, brick),
    List(brick, grass, grass, grass, grass, grass, grass, grass, grass, grass, grass, grass, brick, grass, grass, grass, grass, brick),
    List(brick, grass, grass, grass, brick, grass, grass, grass, grass, grass, grass, grass, brick, grass, grass, grass, grass, brick),
    List(brick, brick, brick, brick, brick, brick, brick, brick, brick, brick, brick, brick, brick, brick, brick, brick, brick, brick)
  )

  val impassableBlocks = List(brick)


  initKeyboard()

  js.timers.setInterval(50) {
    render
  }


  def render() {
    if (mainTextures.isReady) {


      var y = 0
      for(row <- map) {
        var x = 0
        for(texture <- row) {
          if (texture != grass) grass.draw(ctx, gridSize * x, gridSize * y)
          texture.draw(ctx, gridSize * x, gridSize * y)

          x = x + 1
        }
        y = y + 1
      }


      playerTexture.draw(ctx, gridSize * player.x, gridSize * player.y)



      var radius = 701

      // Не хейтить, потом вынесу в другое место!!!111
//      ctx.beginPath()
//      ctx.rect(0, 0, map(0).size * gridSize, player.y * (gridSize + 1) - radius)
//      ctx.rect(0, player.y * gridSize + radius, map(0).size * gridSize, map.size * gridSize)
//      ctx.fill()


//      ctx.save()
//      ctx.beginPath
//      ctx.globalCompositeOperation = "source-over"
//      ctx.rect(0, 0, map(0).size * gridSize, map.size * gridSize)
//      ctx.globalCompositeOperation = "destination-out"
//      ctx.arc(player.x * gridSize, player.y * gridSize, radius, 0, Math.PI * 2)
//      ctx.fill()
//      ctx.restore()
      // ctx.fillRect(0, 0, map(0).size * gridSize, map.size * gridSize)


      for (angle <- 0 until 360 by 1) {
        var rad = angle * Math.PI / 180
        var isNotVisiable = false

        for (r <- 0 until radius by gridSize) {
          var x = gridSize * player.x
          var y = gridSize * player.y
          var endX = x + sin(angle) * r
          var endY = y + cos(angle) * r

          var mapX = round(endX / gridSize).toInt
          var mapY = round(endY / gridSize).toInt

          if (mapX < 0) mapX = 0
          if (mapY < 0) mapY = 0
          if (mapX > map(0).size - 1) mapX = map(0).size - 1
          if (mapY > map.size - 1) mapY = map.size - 1

          if (impassableBlocks.contains(map(mapY)(mapX))) {
            isNotVisiable = true
          } else
          if (isNotVisiable) {
            ctx.beginPath()
            ctx.rect(mapX * gridSize, mapY * gridSize, mapX + gridSize, mapY + gridSize)
            ctx.fill()
          }

        }
      }
    }
  }

  def isValidPlayerPosition(x: Int, y: Int): Boolean = {
    !impassableBlocks.contains(map(player.y + y)(player.x + x))
  }


  def initKeyboard(): Unit = {
    window.addEventListener("keydown", (e: KeyboardEvent) => {

      if(e.keyCode == KeyCode.Left && isValidPlayerPosition(-1, 0)) {
        player.x -= 1
      }

      if(e.keyCode == KeyCode.Right && isValidPlayerPosition(1, 0)) {
        player.x += 1
      }

      if(e.keyCode == KeyCode.Up && isValidPlayerPosition(0, -1)) {
        player.y -= 1
      }

      if(e.keyCode == KeyCode.Down && isValidPlayerPosition(0, 1)) {
        player.y += 1
      }
    }, false)
  }
}
