package game

object Screens {
  var selectedScreen = ""
  var screensRenders: Map[String, Function0[Unit]] = Map()

  def add(screenName: String, renderMethod: Function0[Unit]): Unit = {
    screensRenders += (screenName -> renderMethod)
  }

  def selectScreen(index: String): Unit = {
    selectedScreen = index
  }

  def render(): Unit = {
    if (selectedScreen != "") {
      screensRenders(selectedScreen)()
    }
  }
}
