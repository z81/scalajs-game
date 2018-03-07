package game

import org.scalajs.dom._

import scala.scalajs.js
import org.scalajs.dom.ext.KeyCode

import scala.collection.mutable.ListBuffer
import scala.math._

class Renderer(players: ListBuffer[Player], _map: String) {
  val gridSize = 16

  val canvas = document.createElement("canvas").asInstanceOf[html.Canvas]
  val ctx = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]
  ctx.scale(2, 2)

  canvas.width = window.innerWidth.toInt
  canvas.height = window.innerHeight.toInt
  document.body.appendChild(canvas)

  var texturePack = new TexturePack()
  texturePack.addSource("main", "assets/images/pack1.png", gridSize)
  texturePack.add("main", 3, 0)("grass")
  texturePack.add("main", 6, 0)("forest")
  texturePack.add("main", 5, 1)("rock")
  texturePack.add("main", 0, 35)("player0")
  texturePack.add("main", 0, 36)("player1")
  texturePack.add("main", 0, 37)("player2")
  texturePack.add("main", 1, 35)("player3")
  texturePack.add("main", 1, 36)("player4")
  texturePack.add("main", 1, 37)("player5")
  texturePack.add("main", 4, 35)("player6")
  texturePack.add("main", 5, 35)("player7")
  texturePack.add("main", 6, 35)("player8")
  texturePack.add("main", 6, 36)("player9")
  texturePack.add("main", 6, 2)("water")
  texturePack.add("main", 5, 23)("monster")
  texturePack.add("main", 0, 43)("die")

  players.foreach(_.texturePack = texturePack)


  val gameMap = new GameMap(map = _map, pack = texturePack)

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

  js.timers.setInterval(50) {
    render
  }

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

    var text = s"${players.filter(_.hp > 0).size - 1} выживших из ${players.size - 1}"
    ctx.fillStyle = "black"
    ctx.fillText(text, 99, 9)
    ctx.fillText(text, 99, 11)
    ctx.fillText(text, 101, 9)
    ctx.fillText(text, 101, 11)
    ctx.fillStyle = "white"
    ctx.fillText(text, 100, 10)
  }

  def isValidPlayerPosition(x: Int, y: Int): Boolean = {
    !impassableBlocks.contains(map(players(0).y + y)(players(0).x + x))
  }

}
