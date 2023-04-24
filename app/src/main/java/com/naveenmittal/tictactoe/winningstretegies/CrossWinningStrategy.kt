package com.naveenmittal.tictactoe.winningstretegies

import com.naveenmittal.tictactoe.interfaces.WinningStrategy
import com.naveenmittal.tictactoe.model.Board
import com.naveenmittal.tictactoe.model.Move

class CrossWinningStrategy: WinningStrategy {
    override fun isWinningMove(board: Board, move: Move): Boolean {
        return false
    }
}