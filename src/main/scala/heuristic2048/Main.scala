package heuristic2048

import scala.scalajs.js
import js.Dynamic.{ global => g }
import js.annotation.JSExport

@JSExport
object Main {
  @JSExport
  def main(): Unit = {
    val paragraph = g.document.createElement("p")
    paragraph.innerHTML = "<strong>It works!</strong>"
    g.document.getElementById("playground").appendChild(paragraph)
  }
}
