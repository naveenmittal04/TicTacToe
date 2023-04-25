package com.naveenmittal.tictactoe.botplayingstrategies

import com.naveenmittal.tictactoe.interfaces.BotPlayingStrategy
import com.naveenmittal.tictactoe.model.Board
import com.naveenmittal.tictactoe.model.Bot
import com.naveenmittal.tictactoe.model.Move

class EasyBotPlayingStrategy : BotPlayingStrategy {
    override fun makeMove(board: Board, bot: Bot): Move {
        for (i in 0 until board.size) {
            for (j in 0 until board.size) {
                if (board.getBoard()[i][j].isEmpty()) {
                    return Move(i, j, bot)
                }
            }
        }
        throw Exception("No move possible")
    }
}