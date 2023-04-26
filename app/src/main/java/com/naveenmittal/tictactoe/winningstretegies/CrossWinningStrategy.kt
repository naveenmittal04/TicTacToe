package com.naveenmittal.tictactoe.winningstretegies

import android.util.Log
import com.naveenmittal.tictactoe.interfaces.WinningStrategy
import com.naveenmittal.tictactoe.model.Board
import com.naveenmittal.tictactoe.model.Move

class CrossWinningStrategy: WinningStrategy {
    private val m : MutableList<MutableMap<Int, Int>> = mutableListOf()
    private val leftCross: Int = 0
    private val rightCross: Int = 1
    private val TAG = "CrossWinningStrategy"
    private var size = 0
    override fun isWinningMove(board: Board, move: Move): Boolean {
        if (m.isEmpty()){
            size = board.size
            for (i in 0 until board.size) {
                m.add(mutableMapOf(Pair(i,0)))
            }
        }
        val col = move.col
        val row = move.row
        if(row == col) {
            val count = m[move.player.getId()][leftCross] ?: 0;
            m[move.player.getId()][leftCross] = count + 1
            if (count + 1 == board.size) {
                Log.d(TAG, "isWinningMove: $m")
                return true
            }
        }
        if(row + col == board.size-1) {
            val count = m[move.player.getId()][rightCross] ?: 0;
            m[move.player.getId()][rightCross] = count + 1
            if (count + 1 == board.size) {
                Log.d(TAG, "isWinningMove: $m")
                return true
            }
        }
        return false
    }

    override fun undoMove(move: Move) {
        val col = move.col
        val row = move.row
        if(row == col) {
            val count = m[move.player.getId()][leftCross] ?: 0;
            m[move.player.getId()][leftCross] = count - 1
        }
        if(row + col == size-1) {
            val count = m[move.player.getId()][rightCross] ?: 0;
            m[move.player.getId()][rightCross] = count - 1
        }
    }

    override fun reset() {
        m.clear()
    }
}