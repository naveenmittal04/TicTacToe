package com.naveenmittal.tictactoe.client

import com.naveenmittal.tictactoe.controller.GameController
import com.naveenmittal.tictactoe.model.Bot
import com.naveenmittal.tictactoe.model.DifficultyLevel
import com.naveenmittal.tictactoe.model.Game
import com.naveenmittal.tictactoe.model.GameState
import com.naveenmittal.tictactoe.model.Player
import com.naveenmittal.tictactoe.model.PlayerType
import com.naveenmittal.tictactoe.winningstretegies.ColumnWinningStrategy
import com.naveenmittal.tictactoe.winningstretegies.RowWinningStrategy

class Client {
    private var size = 3
    private var game = Game.Builder()
        .setBoardSize(size)
        .setPlayer(Player(0, "X", PlayerType.HUMAN))
        .setPlayer(Bot(1, DifficultyLevel.EASY, "O"))
        .setWinningStrategy(ColumnWinningStrategy())
        .setWinningStrategy(RowWinningStrategy())
        .build()
    private var gameController = GameController()

}