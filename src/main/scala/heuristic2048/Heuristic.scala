package heuristic2048

object Heuristic {  
  def getProposedMove(state: GameState): Move = {
    Move.all.maxBy(m => score(state.move(m)))
  }
  
  private def score(state: GameState, depth: Int = 4): Float = {
    def emptyCount(state: GameState) = state.cells.flatten.count(_ == EmptyCell)

    depth match {
      case 0 => emptyCount(state)
      case _ => Move.all.map(move => score(state.move(move), depth - 1)).sum.toFloat / Move.all.length
    }
  }
}