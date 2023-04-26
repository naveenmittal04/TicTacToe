package com.naveenmittal.tictactoe.winningstretegies

import android.util.Log
import com.naveenmittal.tictactoe.interfaces.WinningStrategy
import com.naveenmittal.tictactoe.model.Board
import com.naveenmittal.tictactoe.model.Move

class RowWinningStrategy: WinningStrategy {
    val m : MutableList<MutableMap<Int, Int>> = mutableListOf()

    override fun isWinningMove(board: Board, move: Move): Boolean {
        if (m.isEmpty()){
            for (i in 0 until board.size) {
                m.add(mutableMapOf(Pair(i,0)))
            }
        }
        val row = move.row
        val count = m[move.player.getId()][row] ?: 0;
        m[move.player.getId()][row] = count + 1
        Log.d("RowWinningStrategy", "isWinningMove: $m")
        if(count+1 == board.size){
            return true
        }
        return false
    }

    override fun undoMove(move: Move) {
        val row = move.row
        val count = m[move.player.getId()][row] ?: 0;
        m[move.player.getId()][row] = count - 1
    }
}