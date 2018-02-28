package game

import org.scalajs.dom.CanvasRenderingContext2D

class UserBar(player: Player, map: GameMap) {
  val uiSprite = new Image("assets/images/ui.png")

  def render(ctx: CanvasRenderingContext2D, gridSize: Int): Unit = {
    ctx.drawImage(uiSprite.element, map.width * gridSize - 115, map.height * gridSize - 80)
    // hp
    ctx.drawImage(
      uiSprite.element,
      120, 0,
      player.hp / 2, 20,
      map.width * gridSize - 70, map.height * gridSize - 80,
      player.hp / 2, 20
    )
    // armor
    ctx.drawImage(
      uiSprite.element,
      120, 20,
      player.armor / 2, 10,
      map.width * gridSize - 70, map.height * gridSize - 60,
      player.armor / 2, 10
    )
    // stamina
    ctx.drawImage(
      uiSprite.element,
      120, 30,
      player.stamina / 2, 10,
      map.width * gridSize - 70, map.height * gridSize - 50,
      player.stamina / 2, 10
    )

    ctx.fillStyle = "white"
    ctx.font = "15px Open-sans"
    ctx.fillText("1", map.width * gridSize - 95, map.height * gridSize - 50)

    ctx.font = "10px Open-sans"
    ctx.fillText(player.hp.toString, map.width * gridSize - 95, map.height * gridSize - 16)
    ctx.fillText(player.armor.toString, map.width * gridSize - 70, map.height * gridSize - 16)
    ctx.fillText(player.stamina.toString, map.width * gridSize - 45, map.height * gridSize - 16)
  }
}
