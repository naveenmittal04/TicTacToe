package com.naveenmittal.tictactoe.model

import com.naveenmittal.tictactoe.interfaces.BotPlayingStrategy

class Bot(
    id: Int,
    private var level: DifficultyLevel,
    symbol: String
    ): Player(id, symbol, PlayerType.BOT) {
    fun setDifficultyLevel(it: DifficultyLevel) {
        level = it
    }

    private val botPlayingStrategy: BotPlayingStrategy = BotPlayingStrategyFactory.getBotPlayingStrategy(level)

    override fun makeMove(board: Board): Move {
        return botPlayingStrategy.makeMove(board, this)
    }
}