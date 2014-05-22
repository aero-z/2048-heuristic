package heuristic2048

import scala.util.Random

object HeuristicPerformanceTest {
  
  def play(state: GameState, pickMove: GameState => Option[Move]): GameState = {
    def playRec(state: GameState): GameState = {
      print(".")
      pickMove(state) match {
        case Some(move) => playRec(state.move(move).spawnRandom2Block)
        case None => state
      }
    }
    val end = playRec(state)
    println()
    end
  }
  
  def main(args: Array[String]): Unit = {    
    val start = new GameState().spawnRandom2Block.spawnRandom2Block
    def randomMove(state: GameState): Option[Move] = {
      state.getPossibleMoves match {
        case Nil => None
        case xs => Some(xs(Random.nextInt(xs.length))._1)
      }
    }
    val end = play(start, Heuristic.getProposedMove)
    //val end = play(start, randomMove)
    println(end)
  }
}