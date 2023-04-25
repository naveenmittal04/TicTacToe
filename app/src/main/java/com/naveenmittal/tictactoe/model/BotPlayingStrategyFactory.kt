package com.naveenmittal.tictactoe.model

import com.naveenmittal.tictactoe.botplayingstrategies.EasyBotPlayingStrategy
import com.naveenmittal.tictactoe.botplayingstrategies.HardBotPlayingStrategy
import com.naveenmittal.tictactoe.botplayingstrategies.MediumBotPlayingStrategy
import com.naveenmittal.tictactoe.interfaces.BotPlayingStrategy

class BotPlayingStrategyFactory {

    companion object{
        fun getBotPlayingStrategy(difficultyLevel: DifficultyLevel): BotPlayingStrategy {
            return when(difficultyLevel){
                DifficultyLevel.EASY -> EasyBotPlayingStrategy()
                DifficultyLevel.MEDIUM -> MediumBotPlayingStrategy()
                DifficultyLevel.HARD -> HardBotPlayingStrategy()
            }
        }
    }
}