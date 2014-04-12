package heuristic2048

object Heuristic {  
  def getProposedMove(state: GameState): Move = {
    state.getPossibleMoves().maxBy({case (move, state) => score(state)})._1
  }
  
  private def score(state: GameState, depth: Int = 4): Float = {
    def allPossible2BlockSpawnings(state: GameState) = {
      val flatCells = state.cells.flatten
      flatCells.zipWithIndex.filter(_._1 == EmptyCell).map({case (c,i) => state.setBlock(i/4, i%4, 2)})
    }
    
    def emptyCount(state: GameState) = state.cells.flatten.count(_ == EmptyCell)

    depth match {
      case 0 => emptyCount(state)
      case _ => 
        val moveScores = state.getPossibleMoves.map(_._2)
                        .map(allPossible2BlockSpawnings(_).distinct) // all possible states after spawning a block
                        .map(states => states.map(score(_, depth-1)).sum / states.length.toFloat) // scores of each move
        moveScores.sum / moveScores.length.toFloat // average of all moves
    }
  }
}