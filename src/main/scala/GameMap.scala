package game

class GameMap(map: String, pack: TexturePack) {
  val texturedMap = map.split("\n").map(_.split("").map((v: String) => pack.getId(v.toInt)))

  def height = texturedMap.size
  def width = texturedMap(0).size
}
