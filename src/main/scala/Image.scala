package game

import org.scalajs.dom._

class Image(src: String) {
  private var ready: Boolean = false

  val element = document.createElement("img").asInstanceOf[raw.HTMLImageElement]
  element.onload = (e: Event) => ready = true
  element.src = src

  def isReady: Boolean = ready
}