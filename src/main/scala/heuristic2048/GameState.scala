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
    def moveHorizontal(cells: List[List[Cell]], move: Move) = {
      cells.map(col => {
        val blocks = col.collect({ case b: BlockCell => b })
        val combined = combine(blocks)
        val padding = List.fill(4 - combined.length)(EmptyCell)
        (move: @unchecked) match {
          case MoveLeft => combined ::: padding
          case MoveRight => padding ::: combined
        }          
      })
    }
    new GameState(move match {
      case MoveLeft => moveHorizontal(cells, MoveLeft)
      case MoveRight => moveHorizontal(cells, MoveRight)
      case MoveUp => moveHorizontal(cells.transpose, MoveLeft).transpose
      case MoveDown => moveHorizontal(cells.transpose, MoveRight).transpose
    })
  }
  
  def setBlock(x: Int, y: Int, v: Int): GameState = {
    new GameState(cells.updated(x, cells(x).updated(y, BlockCell(v))))
  }
}