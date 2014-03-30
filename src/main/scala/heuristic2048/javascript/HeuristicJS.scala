package heuristic2048.javascript

import scala.scalajs.js
import js.Dynamic.{ global => g }
import js.annotation.JSExport
import heuristic2048._

@JSExport
class GameStateJS(state: GameState) {
  private val moveMapping: Map[Move, String] = Map(
    MoveUp -> "up",
    MoveDown -> "down",
    MoveLeft -> "left",
    MoveRight -> "right"
  )
  
  @JSExport
  def this() = this(new GameState())
  
  @JSExport
  def getCell(x: Int, y: Int) = {
    state.cells(x)(y) match {
      case EmptyCell => 0
      case BlockCell(v) => v 
    }
  }
  
  @JSExport
  def move(move: String) = {
    new GameStateJS(state.move(moveMapping.find(_._2 == move).get._1))
  }
  
  @JSExport
  def setBlock(x: Int, y: Int, v: Int) = {
    new GameStateJS(state.setBlock(x, y, v))
  }
  
  @JSExport
  def getProposedMove() = {
    moveMapping(Heuristic.getProposedMove(state))
  }
}