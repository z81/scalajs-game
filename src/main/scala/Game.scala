package game

import jdk.nashorn.api.scripting.JSObject
import org.scalajs.dom._
import org.scalajs.dom.ext.KeyCode
import scala.collection.mutable.ListBuffer

import scala.math.{abs, round}
import scalajs.js._

class Game {
  val ws = new GameClient()
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
    var map: String = ""
    var players: ListBuffer[Player] = new ListBuffer[Player]()
    var renderer: Renderer = null


      ws.onAction((action: String, data) => {
      println(action, data)

       action match {
         case "LOAD_MAP" => {
           map = data.asInstanceOf[Dictionary[String]]("map")

           renderer = new Renderer(players, map)
           initKeyboard(players, renderer)
         }
         case "LOAD_PLAYERS" => {
          data.asInstanceOf[Array[Dictionary[String]]].foreach(p => {

            players += new Player(
              name = p("username"),
              xPos = p("x").asInstanceOf[Int],
              yPos = p("y").asInstanceOf[Int],
              maxVisibleRadius = 80,
              skinName = p("skin")
            )
          })
         }
         case "JOIN" => {
           val p = data.asInstanceOf[Dictionary[String]]

           players += new Player(
             name = p("username"),
             xPos = p("x").asInstanceOf[Int],
             yPos = p("y").asInstanceOf[Int],
             maxVisibleRadius = 80,
             skinName = p("skin")
           )
           players.last.texturePack = renderer.texturePack
         }
         case "MOVE" => {
           val p = data.asInstanceOf[Dictionary[String]]

           players.foreach((player: Player) => {
             if (p("username") == player.username) {
               player.x = p("x").asInstanceOf[Int]
               player.y = p("y").asInstanceOf[Int]
             }
           })
         }
         case "LEAVE" => {
           val p = data.asInstanceOf[Dictionary[String]]

           var i = 0
           players.foreach((player: Player) => {
             if (p("username") == player.username) {
               players.remove(i)
             }
             i = i + 1
           })
         }
         case "DAMAGE" => {
           val p = data.asInstanceOf[Dictionary[String]]

           players.foreach((player: Player) => {
             if (p("username") == player.username) {
               player.armor = p("armor").asInstanceOf[Int]
               player.hp = p("hp").asInstanceOf[Int]
             }
           })
         }
       }
    })

    ws.action("JOIN", Dictionary("username" -> username))

  }

  def initKeyboard(players: ListBuffer[Player], renderer: Renderer): Unit = {
    window.addEventListener("keydown", (e: KeyboardEvent) => {
      if (players(0).hp > 0) {
        if ((e.keyCode == KeyCode.Left || e.keyCode == KeyCode.A) && renderer.isValidPlayerPosition(-1, 0)) {
          players(0).move(-1, 0)
        }

        if ((e.keyCode == KeyCode.Right || e.keyCode == KeyCode.D) && renderer.isValidPlayerPosition(1, 0)) {
          players(0).move(1, 0)
        }

        if ((e.keyCode == KeyCode.Up || e.keyCode == KeyCode.W) && renderer.isValidPlayerPosition(0, -1)) {
          players(0).move(0, -1)
        }

        if ((e.keyCode == KeyCode.Down || e.keyCode == KeyCode.S) && renderer.isValidPlayerPosition(0, 1)) {
          players(0).move(0, 1)
        }

        if (e.keyCode == KeyCode.F && players(0).isMonster) {
          players.foreach((p: Player) => {
            if (p != players(0)) {

              val cx = p.x - players(0).x
              val cy = p.y - players(0).y

              if (Math.abs(cx) <= 1 && Math.abs(cy) <= 1) {
                if (p.armor > 0) {
                  p.armor -= players(0).damage

                  if (p.armor < 0) {
                    p.armor = 0
                  }
                } else
                if (p.hp > 0) {
                  p.hp -= players(0).damage

                  if (p.hp < 0) {
                    p.hp = 0
                  }
                }

                ws.action("DAMAGE", Dictionary(
                  "username" -> p.username,
                  "hp" -> p.hp,
                  "armor" -> p.armor
                ))
              }
            }
          })
        }

        ws.action("MOVE", Dictionary(
          "x" -> players(0).x,
          "y" -> players(0).y
        ))
      }
    }, false)


//    document.body.addEventListener("click", (e: MouseEvent) => {
//      var x = round(e.clientX / renderer.gridSize).toInt
//      var y = round(e.clientY / renderer.gridSize).toInt
//
//      if (abs(mousePosX - x) < 5 && abs(renderer.mousePosY - y) < 5) {
//        mousePosY = y
//        mousePosX = x
//      }
//    }, false)
  }


}
