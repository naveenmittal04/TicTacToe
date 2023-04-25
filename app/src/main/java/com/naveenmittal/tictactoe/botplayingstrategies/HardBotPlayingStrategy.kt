package com.naveenmittal.tictactoe.botplayingstrategies

import com.naveenmittal.tictactoe.interfaces.BotPlayingStrategy
import com.naveenmittal.tictactoe.model.Board
import com.naveenmittal.tictactoe.model.Bot
import com.naveenmittal.tictactoe.model.Move

class HardBotPlayingStrategy: BotPlayingStrategy {
    override fun makeMove(board: Board, bot: Bot): Move {
        throw Exception("Not yet implemented")
    }
}