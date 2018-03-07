package game

import org.scalajs.dom.CanvasRenderingContext2D

import scala.math.{cos, round, sin}

class WarFog(gridSize: Int, impassableBlocks: Seq[ImageRegion]) {

  def render(ctx: CanvasRenderingContext2D, player: Player, map: Array[Array[ImageRegion]]): Unit = {
    val mapHeight = map.size
    var mapWidth = map(0).size
    val mapPxWidth = mapWidth * gridSize
    val mapPxHeight = mapHeight * gridSize
    var maxVisibleRadius = player.visibleRadius



    ctx.beginPath()
    ctx.fillStyle = "rgb(30,30,30)"
    ctx.rect(0, 0, mapPxWidth, player.y * (gridSize) - maxVisibleRadius)
    ctx.rect(0, player.y * gridSize + maxVisibleRadius, mapPxWidth, mapPxHeight)
    ctx.rect(0, 0, player.x * (gridSize) - maxVisibleRadius, mapPxHeight)
    ctx.rect(player.x * (gridSize) + maxVisibleRadius, 0, mapPxWidth, mapPxHeight)
    // ctx.fill()



    if (!player.isMonster) {
      for (angle <- 0 until 360 by 1) {
        var rad = angle * Math.PI / 180
        var isNotVisiable = false

        for (r <- 0 until player.visibleRadius * 10 by gridSize) {
          var x = gridSize * player.x
          var y = gridSize * player.y
          var endX = x + sin(angle) * r
          var endY = y + cos(angle) * r

          var mapX = round(endX / gridSize).toInt
          var mapY = round(endY / gridSize).toInt

          if (mapX < 0) mapX = 0
          if (mapY < 0) mapY = 0
          if (mapX > mapWidth - 1) mapX = mapWidth - 1
          if (mapY > mapHeight - 1) mapY = mapHeight - 1

          if (impassableBlocks.contains(map(mapY)(mapX))) {
            isNotVisiable = true
          } else if (isNotVisiable) {
            ctx.rect(mapX * gridSize, mapY * gridSize, gridSize, gridSize)
          }

          //          if (r > radius * 0.75) {
          //            val colorVal = (100 / radius * r) / 100
          //
          //            //ctx.beginPath()
          //            // ctx.fillStyle = "black"
          //            ctx.rect(mapX * gridSize, mapY * gridSize, mapX + gridSize, mapY + gridSize)
          //            //ctx.fill()
          //          }
        }
      }
    }

    ctx.fill()
  }
}
