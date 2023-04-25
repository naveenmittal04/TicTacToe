package com.naveenmittal.tictactoe.ui.viewmodel

import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.naveenmittal.tictactoe.controller.GameController
import com.naveenmittal.tictactoe.model.Board
import com.naveenmittal.tictactoe.model.Game
import com.naveenmittal.tictactoe.model.GameState
import com.naveenmittal.tictactoe.model.Player
import com.naveenmittal.tictactoe.winningstretegies.ColumnWinningStrategy
import com.naveenmittal.tictactoe.winningstretegies.RowWinningStrategy
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
                .setWinningStrategy(RowWinningStrategy())
                .build()
            ticTacToeState.value = TicTacToeState.IN_PROGRESS
            currentPlayer.value = game!!.getCurrentPlayer()
            if (currentPlayer.value!!.isBot()) {
                gameController.makeMove( game!!)
                currentPlayer.value = game!!.getCurrentPlayer()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            //Toast.makeText(null, e.message, Toast.LENGTH_LONG).show()
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
                if (currentPlayer.value!!.isBot()) {
                    gameController.makeMove( game!!)
                    currentPlayer.value = game!!.getCurrentPlayer()
                } else {
                    gameController.makeMove(row, col, game!!)
                }
                if(game!!.getGameState() == GameState.SUCCESS || game!!.getGameState() == GameState.DRAW) {
                    ticTacToeState.value = TicTacToeState.STOP
                }
                currentPlayer.value = game!!.getCurrentPlayer()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            //Toast.makeText(, e.message, Toast.LENGTH_LONG).show()
        }

    }

    fun getCurrentPlayer() : MutableStateFlow<Player?> {
        return currentPlayer
    }

    fun getWinner(): String? {
        return game?.getWinner()
    }

    fun getBoard(): Board {
        return game!!.getBoard()
    }
}