package game

class GameMap(map: Seq[Seq[Int]], pack: TexturePack) {

  val texturedMap = map.map(_.map(pack.getId(_)))

  def height = texturedMap.size
  def width = texturedMap(0).size
}
