package heuristic2048

object Heuristic {  
  def getProposedMove(state: GameState): Option[Move] = {
    state.getPossibleMoves match {
      case Nil => None
      case moves => Some(moves.maxBy({case (move, state) => score(state)})._1)
    }
  }
  
  private def score(state: GameState, depth: Int = 3): Float = {    
    val emptyCount = state.cells.flatten.count(_ == EmptyCell)

    if (depth == 0 || emptyCount > 6) {
      score0(state) + emptyCount * 2
    } else {
      val moveScores = state.getPossibleMoves.map(_._2)
                      .map(_.allPossible2BlockSpawnings.distinct) // all possible states after spawning a block
                      .map(states => states.map(score(_, depth-1)).sum / states.length.toFloat) // scores of each move
      moveScores.sum / moveScores.length.toFloat // average of all moves
    }
  }
  
  def score0(state: GameState): Float = {
    def verticalScore(cells: List[List[Cell]]) = 
	  cells.map(col => col.foldLeft[(Cell, Float)]((EmptyCell, 0))((b, c) => (b._1, c) match {
	    case (EmptyCell, _) => (c, b._2)
	    case (_, EmptyCell) => (EmptyCell, b._2)
	    case (BlockCell(e1), BlockCell(e2)) => (c, b._2 + 1.0f/((e1 - e2).abs + 1))
	  })._2).sum
	verticalScore(state.cells) + verticalScore(state.cells.transpose)
  }
}