package com.naveenmittal.tictactoe.winningstretegies

import com.naveenmittal.tictactoe.interfaces.WinningStrategy
import com.naveenmittal.tictactoe.model.Board
import com.naveenmittal.tictactoe.model.Move

class ColumnWinningStrategy: WinningStrategy {
    val m : MutableList<MutableMap<Int, Int>> = mutableListOf()

    override fun isWinningMove(board: Board, move: Move): Boolean {
        if (m.isEmpty()){
            for (i in 0 until board.size) {
                m.add(mutableMapOf(Pair(i,0)))
            }
        }
        val col = move.col
        val count = m[move.player.getId()][col] ?: 0;
        if(count == board.size){
            return true
        }
        m[move.player.getId()][col] = count + 1
        return false
    }
}