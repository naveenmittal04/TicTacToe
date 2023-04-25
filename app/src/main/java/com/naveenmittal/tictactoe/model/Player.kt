package com.naveenmittal.tictactoe.model

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

open class Player(
    private val id: Int,
    private var symbol: String,
    private var playerType: PlayerType
) {

    fun getId(): Int {
        return id
    }

    fun getSymbol(): String {
        return symbol
    }

    fun setSymbol(symbol: String){
        this.symbol = symbol
    }

    fun getPlayerType(): PlayerType {
        return playerType
    }

    fun setPlayerType(type: String) {
        try {
            this.playerType = PlayerType.valueOf(type)
        } catch (e: IllegalArgumentException) {
            this.playerType = PlayerType.HUMAN
        }
    }

    fun isBot(): Boolean {
        return playerType == PlayerType.BOT
    }


    open fun makeMove(board: Board): Move {
        // Invalid move
        throw Exception("Invalid move")
    }

//    fun makeMove(): Move {
//        return Move(0, 0)
//    }
}
