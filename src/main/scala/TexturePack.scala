package game

import scala.collection.mutable.ListBuffer

class TexturePack {
  var packs: Map[String, ImageMap] = Map()
  var textures: Map[String, ImageRegion] = Map()
  var texturesByIndex: ListBuffer[ImageRegion] = ListBuffer()

  def addSource(name: String, url: String, gridSize: Int): Unit = {
    packs += (name -> new ImageMap(url, gridSize))
  }


  def add(packName: String, x: Int, y: Int): Function1[String, Unit] = {
    (textureName: String) => {
      val pack = packs(packName).getTexture(x, y)
      textures += (textureName -> pack)
      texturesByIndex += pack
    }
  }

  def get(textureName: String): ImageRegion = {
    textures(textureName)
  }

  def getId(index: Int): ImageRegion = {
    texturesByIndex(index)
  }
}
