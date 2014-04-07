package heuristic2048

sealed trait Cell
case object EmptyCell extends Cell
case class BlockCell(val number: Int) extends Cell

sealed trait Move
case object MoveUp extends Move
case object MoveDown extends Move
case object MoveLeft extends Move
case object MoveRight extends Move

class GameState(val cells: List[List[Cell]]) {
  def this() = this(List.fill(4, 4)(EmptyCell))

  def move(move: Move): GameState = {
    def combine(line: List[BlockCell]): List[BlockCell] = {
      if (line.length < 2) line
      else {
        if (line(0) == line(1))
          BlockCell(line(0).number * 2) :: combine(line.drop(2))
        else
          line(0) :: combine(line.tail)
      }
    }
    def moveLeft(cells: List[List[Cell]]) = {
      cells.map(col => {
        val blocks = col.collect({ case b: BlockCell => b })
        val combined = combine(blocks)
        val padding = List.fill(4 - combined.length)(EmptyCell)
        combined ::: padding        
      })
    }
    new GameState(move match {
      case MoveLeft => moveLeft(cells)
      case MoveRight => moveLeft(cells.map(_.reverse)).map(_.reverse)
      case MoveUp => moveLeft(cells.transpose).transpose
      case MoveDown => moveLeft(cells.transpose.map(_.reverse)).map(_.reverse).transpose
    })
  }
  
  def setBlock(x: Int, y: Int, v: Int): GameState = {
    new GameState(cells.updated(x, cells(x).updated(y, BlockCell(v))))
  }
}