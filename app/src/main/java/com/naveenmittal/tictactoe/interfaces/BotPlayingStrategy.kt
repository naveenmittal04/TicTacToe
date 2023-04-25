package com.naveenmittal.tictactoe.interfaces

import com.naveenmittal.tictactoe.model.Board
import com.naveenmittal.tictactoe.model.Bot
import com.naveenmittal.tictactoe.model.Move

interface BotPlayingStrategy {
    fun makeMove(board: Board, bot: Bot): Move
}