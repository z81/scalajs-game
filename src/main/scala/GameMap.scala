package game

class GameMap(map: Seq[Seq[Int]], textures: Seq[ImageRegion]) {

  val texturedMap = map.map(_.map(textures(_)))

  def height = texturedMap.size
  def width = texturedMap(0).size
}
