package com.naveenmittal.tictactoe.model

import com.naveenmittal.tictactoe.interfaces.BotPlayingStrategy

class Bot(
    id: Int,
    private val level: DifficultyLevel,
    symbol: String
    ): Player(id, symbol, PlayerType.BOT) {
        private val botPlayingStrategy: BotPlayingStrategy = BotPlayingStrategyFactory.getBotPlayingStrategy(level)

}