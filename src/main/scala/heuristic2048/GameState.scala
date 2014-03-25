package heuristic2048

sealed trait Cell
case object EmptyCell extends Cell
case class BlockCell(number: Int) extends Cell

sealed trait Move
case object MoveUp extends Move
case object MoveDown extends Move
case object MoveLeft extends Move
case object MoveRight extends Move

case class Block(x: Int, y: Int, v: Int)

class GameState(val cells: List[List[Cell]]) {
  def this() = this(List.fill(4, 4)(EmptyCell))
  
  def performMove(move: Move) = {
    ???
  }
  
  def setBlock(block: Block) = block match { case Block(x,y,v) =>
    new GameState(cells.updated(x, cells(x).updated(y, BlockCell(v))))
  }
}