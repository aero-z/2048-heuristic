package heuristic2048

import scala.util.Random

sealed abstract class Cell(val value: Int)
case object EmptyCell extends Cell(0)
case class BlockCell(val exp: Int) extends Cell(exp)

sealed abstract class Move(override val toString: String)
object Move {
  case object Up extends Move("up")
  case object Down extends Move("down")
  case object Left extends Move("left")
  case object Right extends Move("right")
  val all = List(Up, Down, Left, Right): List[Move]
  def fromString(s: String) = all.find(_.toString == s).get
}

case class GameState(val cells: List[List[Cell]]) {
  def this() = this(List.fill(4, 4)(EmptyCell))
  
  lazy val getPossibleMoves: List[(Move, GameState)] = {
    Move.all.map(m => (m, move(m))).filter(_._2 != this)
  }

  def move(move: Move): GameState = {
    def combineLine(line: List[BlockCell]): List[BlockCell] = {
      if (line.length < 2) line
      else {
        if (line(0) == line(1))
          BlockCell(line(0).exp + 1) :: combineLine(line.drop(2))
        else
          line(0) :: combineLine(line.tail)
      }
    }
    def moveLeft(cells: List[List[Cell]]) = {
      cells.map(col => {
        val blocks = col.collect({ case b: BlockCell => b })
        val combined = combineLine(blocks)
        val padding = List.fill(4 - combined.length)(EmptyCell)
        combined ::: padding        
      })
    }
    new GameState(move match {
      case Move.Left => moveLeft(cells)
      case Move.Right => moveLeft(cells.map(_.reverse)).map(_.reverse)
      case Move.Up => moveLeft(cells.transpose).transpose
      case Move.Down => moveLeft(cells.transpose.map(_.reverse)).map(_.reverse).transpose
    })
  }

  // this should be removed
  lazy val allPossible2BlockSpawnings: List[GameState] = {
    cells.flatten.zipWithIndex.filter(_._1 == EmptyCell).map({ case (c, i) => setBlock(i / 4, i % 4, 1) })
  }
  
  lazy val allEmptyPos: List[(Int, Int)] = {
    cells.flatten.zipWithIndex.collect({case (EmptyCell, i) => (i / 4, i % 4)})
  }
  
  def spawnRandomBlock: GameState = {
    val (x, y) = allEmptyPos(Random.nextInt(allEmptyPos.length))
    val v = if (Random.nextInt(10) == 0) 2
            else 1
    setBlock(x, y, v)
  }
  
  def setBlock(x: Int, y: Int, v: Int): GameState = {
    val block =
      if (v == 0) EmptyCell
      else BlockCell(v)
    new GameState(cells.updated(x, cells(x).updated(y, block)))
  }
  
  override lazy val toString = {
    cells.map(_.map(_ match {
      case EmptyCell => "."
      case BlockCell(n) => (1 << n).toString
    }).mkString("\t")).mkString("\n")
  }
}