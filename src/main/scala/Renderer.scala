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
  val rock = mainTextures getTexture(5, 1)
  val cursorHoverTexture = mainTextures getTexture(3, 42)
  val playerTexture = mainTextures getTexture(0, 17)
  val player2Texture = mainTextures getTexture(0, 18)

  var player = new Player(
    xPos = 5,
    yPos = 5,
    maxVisibleRadius = 70,
    name = "Sawaxon",
    texture = playerTexture
  )

  var players = List(player,  new Player(
    xPos = 7,
    yPos = 5,
    maxVisibleRadius = 70,
    name = "Gleb",
    texture = player2Texture
  ))


//  val map = List(
//    List(rock, rock, rock, rock, rock, rock, rock, rock, rock, rock, rock, rock, rock, rock, rock, rock, rock, rock),
//    List(rock, grass, grass, grass, grass, grass, grass, grass, brick, grass, grass, grass, grass, grass, grass, grass, grass, brick),
//    List(rock, grass, grass, grass, grass, grass, grass, grass, brick, grass, grass, grass, grass, grass, grass, grass, grass, brick),
//    List(rock, grass, grass, grass, grass, grass, grass, grass, brick, grass, grass, grass, grass, grass, grass, grass, grass, brick),
//    List(rock, grass, grass, grass, grass, grass, grass, grass, brick, grass, grass, grass, brick, grass, grass, grass, grass, brick),
//    List(rock, brick, brick, grass, grass, grass, grass, grass, brick, grass, grass, grass, brick, grass, grass, grass, grass, brick),
//    List(rock, grass, brick, brick, grass, grass, grass, grass, brick, grass, grass, grass, brick, brick, brick, brick, brick, brick),
//    List(rock, grass, grass, brick, grass, grass, brick, brick, brick, brick, grass, grass, brick, grass, grass, grass, grass, brick),
//    List(rock, grass, grass, brick, brick, grass, grass, grass, grass, grass, grass, grass, brick, grass, grass, grass, grass, brick),
//    List(rock, grass, grass, grass, brick, grass, grass, grass, grass, grass, grass, grass, brick, grass, grass, grass, grass, brick),
//    List(rock, grass, grass, grass, brick, grass, grass, grass, grass, grass, grass, grass, brick, grass, grass, grass, grass, brick),
//    List(rock, grass, grass, grass, brick, grass, grass, grass, grass, grass, grass, grass, grass, grass, grass, grass, grass, brick),
//    List(rock, grass, grass, grass, grass, grass, grass, grass, rock, rock, grass, grass, grass, grass, grass, grass, grass, brick),
//    List(rock, grass, grass, grass, grass, grass, grass, grass, rock, rock, grass, grass, brick, grass, grass, grass, grass, brick),
//    List(rock, grass, grass, grass, brick, grass, grass, grass, grass, grass, grass, grass, brick, grass, grass, grass, grass, brick),
//    List(rock, rock, rock, rock, rock, rock, rock, rock, rock, rock, rock, rock, rock, rock, rock, rock, rock, rock)
//  )
  val gameMap = new GameMap(List(
    List(2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2),
    List(2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2),
    List(2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2),
    List(2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2),
    List(2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2),
    List(2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2),
    List(2, 2, 1, 1, 1, 2, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2),
    List(2, 1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2),
    List(2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2),
    List(2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2),
    List(2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2),
    List(2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2),
    List(2, 1, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2),
    List(2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2),
    List(2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2),
    List(2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2),
    List(2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2),
    List(2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2),
    List(2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2),
    List(2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2),
    List(2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)
  ), Seq(
    brick,
    grass,
    rock
  ))

  val map = gameMap.texturedMap
  val mapHeight = map.size
  var mapWidth = map(0).size

  val impassableBlocks = List(brick, rock)
  val warFog = new WarFog(gridSize, impassableBlocks)
  var userUi = new UserBar(player, gameMap)

  var mousePosX = 0
  var mousePosY = 0


  canvas.width = mapWidth * gridSize
  canvas.height = mapHeight * gridSize
  initKeyboard()

  js.timers.setInterval(50) {
    render
  }


  def render() {
    if (mainTextures.isReady) {


      var y = 0
      for (row <- map) {
        var x = 0
        for (texture <- row) {
          if (texture != grass) grass.draw(ctx, gridSize * x, gridSize * y)
          texture.draw(ctx, gridSize * x, gridSize * y)

          x = x + 1
        }
        y = y + 1
      }



      for(player <- players) {
        player.render(ctx, gridSize)
      }
      warFog.render(ctx, player, map)

      userUi.render(ctx, gridSize)

      //cursorHoverTexture.draw(ctx, mousePosX * gridSize, mousePosY * gridSize)
    }
  }

  def isValidPlayerPosition(x: Int, y: Int): Boolean = {
    !impassableBlocks.contains(map(player.y + y)(player.x + x))
  }


  def initKeyboard(): Unit = {
    window.addEventListener("keydown", (e: KeyboardEvent) => {

      if((e.keyCode == KeyCode.Left || e.keyCode ==  KeyCode.A) && isValidPlayerPosition(-1, 0)) {
        player.move(-1, 0)
      }

      if((e.keyCode == KeyCode.Right || e.keyCode ==  KeyCode.D) && isValidPlayerPosition(1, 0)) {
        player.move(1, 0)
      }

      if((e.keyCode == KeyCode.Up || e.keyCode ==  KeyCode.W) && isValidPlayerPosition(0, -1)) {
        player.move(0, -1)
      }

      if((e.keyCode == KeyCode.Down || e.keyCode ==  KeyCode.S) && isValidPlayerPosition(0, 1)) {
        player.move(0, 1)
      }
    }, false)


    document.body.addEventListener("click", (e: MouseEvent) => {
      var x = round(e.clientX / gridSize).toInt
      var y = round(e.clientY / gridSize).toInt

      if (abs(mousePosX - x) < 5 && abs(mousePosY - y) < 5) {
        mousePosY = y
        mousePosX = x
      }
    }, false)
  }
}
