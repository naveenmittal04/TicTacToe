package com.naveenmittal.tictactoe.interfaces

import com.naveenmittal.tictactoe.model.Board
import com.naveenmittal.tictactoe.model.Move

interface WinningStrategy {
    fun isWinningMove(board: Board, move: Move) : Boolean
}