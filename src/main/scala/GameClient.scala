package game

import org.scalajs.dom._

import scalajs.js._
import scala.scalajs.js._

class GameClient {
  val ws = connect("")


  def action(name: String, data: Dictionary[Any]): Unit = {
    val d = JSON.stringify(Dictionary(
      "action" -> name,
      "data"   -> data
    ))

    ws.send(d)
  }

  def connect(endpoint: String) = {
    val loc = window.location
    val wsProtocol = if (loc.protocol == "http:") "ws:" else "wss:"
    val wsUrl = s"${wsProtocol}//${loc.host}:8080/$endpoint"
    new WebSocket(url = wsUrl)
  }

  def onAction(clb: Function2[String, Any, Unit]): Unit = {
    ws.onmessage = (event: MessageEvent) => {
      val obj = JSON.parse(event.data.asInstanceOf[String])
      val action = obj.action.asInstanceOf[String]
      val data = obj.data.asInstanceOf[Any]

      if (action.length > 0) {
        clb(action, data)
      }
    }
  }
}
