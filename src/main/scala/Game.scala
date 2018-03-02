package game

import org.scalajs.dom._

class Game {
  initSelectNameForm()


  def initSelectNameForm(): Unit ={
    val root = document.getElementById("root")
    val bgNode = document.createElement("div")
    bgNode.classList.add("select-form")
    root.appendChild(bgNode)

    val inputWrapper = document.createElement("div")
    inputWrapper.classList.add("select-form-input-wrapper")
    bgNode.appendChild(inputWrapper)

    val text = document.createElement("div")
    text.textContent = "Nickname:"
    inputWrapper.appendChild(text)

    val input = document.createElement("input").asInstanceOf[html.Input]
    input.setAttribute("type", "text")
    inputWrapper.appendChild(input)
    input.focus()

    val button = document.createElement("button")
    button.textContent = "Join"
    inputWrapper.appendChild(button)

    button.addEventListener("click", (e: MouseEvent) => {
      val inputValue = input.value
      if (inputValue.trim().length == 0) return
      bgNode.classList.add("hidden")

      initGame(inputValue)
    })
  }


  def initGame(username: String): Unit = {
    var players = List(new Player(
      xPos = 5,
      yPos = 5,
      maxVisibleRadius = 70,
      name = username,
      skinName = "player"
    ))

    val renderer = new Renderer(players)
  }
//  ws("")
//
//  def ws(endpoint: String) = {
//    val loc = window.location
//    val wsProtocol = if (loc.protocol == "http:") "ws:" else "wss:"
//    val wsUrl = s"${wsProtocol}//${loc.host}/$endpoint"
//    new WebSocket(url = wsUrl)
//  }
}
