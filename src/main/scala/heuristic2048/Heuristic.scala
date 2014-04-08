package heuristic2048

object Heuristic {  
  def getProposedMove(state: GameState): Move = {
    Move.all.maxBy(m => score(state.move(m)))
  }
  
  private def score(state: GameState, depth: Int = 0): Int = {
    2
  }
}