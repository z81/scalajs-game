package game

import org.scalajs.dom._
import scala.scalajs.js
import org.scalajs.dom.ext.KeyCode
import scala.math._

class Renderer(players: List[Player]) {
  val gridSize = 16

  val canvas = document.createElement("canvas").asInstanceOf[html.Canvas]
  val ctx = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]
  ctx.scale(2, 2)

  canvas.width = window.innerWidth.toInt
  canvas.height = window.innerHeight.toInt
  document.body.appendChild(canvas)

  var texturePack = new TexturePack()
  texturePack.addSource("main", "assets/images/pack1.png", gridSize)
  texturePack.add("main", 6, 0)("forest")
  texturePack.add("main", 3, 0)("grass")
  texturePack.add("main", 5, 1)("rock")
  texturePack.add("main", 0, 17)("player")


  players.foreach(_.texturePack = texturePack)


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
  ), texturePack)

  val map = gameMap.texturedMap
  val mapHeight = map.size
  var mapWidth = map(0).size

  val impassableBlocks = List(texturePack.get("forest"), texturePack.get("rock"))
  val warFog = new WarFog(gridSize, impassableBlocks)
  var userUi = new UserBar(players(0), gameMap)

  var mousePosX = 0
  var mousePosY = 0


  canvas.width = mapWidth * gridSize
  canvas.height = mapHeight * gridSize
  initKeyboard()

  js.timers.setInterval(50) {
    render
  }


  val menuGradient = ctx.createLinearGradient(0, 0, 300, 300)
  menuGradient.addColorStop(0, "black")
  menuGradient.addColorStop(1, "green")

  Screens.add("game",  () => {
    if (texturePack.packs("main").isReady) {


      var y = 0
      for (row <- map) {
        var x = 0
        for (texture <- row) {
          if (texture != texturePack.get("grass")) texturePack.get("grass").draw(ctx, gridSize * x, gridSize * y)
          texture.draw(ctx, gridSize * x, gridSize * y)

          x = x + 1
        }
        y = y + 1
      }



      for(player <- players) {
        player.render(ctx, gridSize)
      }
      warFog.render(ctx, players(0), map)

      userUi.render(ctx, gridSize)

      //cursorHoverTexture.draw(ctx, mousePosX * gridSize, mousePosY * gridSize)
    }
  })

  Screens.add("selectName", () => {
//    val width = 200
//    val height = 30
//    val x = mapWidth / 2 * gridSize - width
//    val y = mapHeight / 2 * gridSize - height
//
//    ctx.fillStyle = menuGradient
//    ctx.fillRect(0, 0, mapWidth * gridSize, mapHeight * gridSize)
//
//    ctx.fillStyle = "white"
//    ctx.strokeStyle = "blue"
//    ctx.fillRect(x, y, width, height)
//    ctx.strokeRect(x, y, width, height)
  })

  Screens.selectScreen("game")

  def render() {
    Screens.render()
  }

  def isValidPlayerPosition(x: Int, y: Int): Boolean = {
    !impassableBlocks.contains(map(players(0).y + y)(players(0).x + x))
  }


  def initKeyboard(): Unit = {
    window.addEventListener("keydown", (e: KeyboardEvent) => {

      if((e.keyCode == KeyCode.Left || e.keyCode ==  KeyCode.A) && isValidPlayerPosition(-1, 0)) {
        players(0).move(-1, 0)
      }

      if((e.keyCode == KeyCode.Right || e.keyCode ==  KeyCode.D) && isValidPlayerPosition(1, 0)) {
        players(0).move(1, 0)
      }

      if((e.keyCode == KeyCode.Up || e.keyCode ==  KeyCode.W) && isValidPlayerPosition(0, -1)) {
        players(0).move(0, -1)
      }

      if((e.keyCode == KeyCode.Down || e.keyCode ==  KeyCode.S) && isValidPlayerPosition(0, 1)) {
        players(0).move(0, 1)
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
