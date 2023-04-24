package com.naveenmittal.tictactoe.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.naveenmittal.tictactoe.controller.GameController
import com.naveenmittal.tictactoe.model.Game
import com.naveenmittal.tictactoe.model.GameState
import com.naveenmittal.tictactoe.model.Player
import com.naveenmittal.tictactoe.winningstretegies.ColumnWinningStrategy
import kotlinx.coroutines.flow.MutableStateFlow

enum class TicTacToeState {
    START,
    IN_PROGRESS,
    STOP
}
class TicTacToeViewModel : ViewModel()  {
    private val gameController = GameController()
    private var ticTacToeState = MutableStateFlow(TicTacToeState.START)
    private var game : Game? = null
    private val currentPlayer = MutableStateFlow<Player?>(null)

    fun getTicTacToeState() : MutableStateFlow<TicTacToeState> {
        return ticTacToeState
    }

    fun startGame(size: Int, players: MutableList<Player>) {
        try {
            game = Game.Builder()
                .setBoardSize(size)
                .setPlayers(players)
                .setWinningStrategy(ColumnWinningStrategy())
                .build()
            ticTacToeState.value = TicTacToeState.IN_PROGRESS
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun printBoard() {
        game?.let {
            gameController.printBoard(game!!)
        }
    }

    fun makeMove(row: Int, col: Int) {
        try {
            game?.let {
                gameController.makeMove(row, col, game!!)
                if(game!!.getGameState() == GameState.SUCCESS || game!!.getGameState() == GameState.DRAW) {
                    ticTacToeState.value = TicTacToeState.STOP
                }
                currentPlayer.value = game!!.getCurrentPlayer()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun getCurrentPlayer() : MutableStateFlow<Player?> {
        return currentPlayer
    }

    fun getWinner(): String {
        return game?.getWinner().toString()
    }
}