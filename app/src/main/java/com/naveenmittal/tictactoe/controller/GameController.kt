package com.naveenmittal.tictactoe.controller

import com.naveenmittal.tictactoe.model.Game

class GameController() {
    fun printBoard(game: Game) {
        game.printBoard()

    }

    fun makeMove(row: Int, col: Int, game: Game) {
        game.makeMove(row, col)
    }

    fun makeMove(game: Game) {
        game.makeMove()
    }

    fun undoMove(game: Game) {
        game.undoMove()
    }

}