package com.naveenmittal.tictactoe.model

import com.naveenmittal.tictactoe.exceptions.DuplicateSymbolException
import com.naveenmittal.tictactoe.exceptions.MoreThanOneBotException
import com.naveenmittal.tictactoe.interfaces.WinningStrategy
import java.lang.Exception

class Game(
    private val size: Int,
    private var players: List<Player>,
    private var board: Board,
    private val winningStrategies : List<WinningStrategy>
) {
    private var gameState: GameState = GameState.IN_PROGRESS
    private var moves: MutableList<Move> = mutableListOf()
    private var currentPlayer: Player = players[0]
    private var winner: Player? = null

    fun getGameState(): GameState {
        return gameState
    }

    fun printBoard() {
        board.printBoard()
    }

    fun makeMove(row: Int, col: Int) {
        val move = Move(row, col, currentPlayer)
        try {
            validateMove(row, col)
            board.makeMove(move)
            moves.add(move)
            if (isWinningMove(move)) {
                gameState = GameState.SUCCESS
                winner = currentPlayer
            } else if (isDraw()) {
                gameState = GameState.DRAW
            } else {
                currentPlayer = getNextPlayer()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    fun makeMove() {
        if(currentPlayer.isBot()) {
            val move = currentPlayer.makeMove(board)
            makeMove(move.row, move.col)
        } else {
            throw Exception("Invalid move")
        }
    }

    fun getNextPlayer(): Player {
        val index = players.indexOf(currentPlayer)
        return players[(index + 1) % players.size]
    }

    fun isDraw() : Boolean{
        return moves.size == size * size
    }

    private fun isWinningMove(move: Move): Boolean {
        for(strategy in winningStrategies) {
            if (strategy.isWinningMove(board, move)) {
                return true
            }
        }
        return false
    }

    private fun validateMove(row: Int, col: Int) {
        if (row >= size || col >= size) {
            throw Exception("Invalid move")
        }
    }

    fun getWinner(): String {
        if (gameState == GameState.DRAW) {
            return "Draw"
        } else if (gameState == GameState.SUCCESS) {
            return winner?.getSymbol() ?: ""
        }
        return GameState.IN_PROGRESS.name
    }

    fun getCurrentPlayer(): Player {
        return currentPlayer
    }

    fun getBoard(): Board {
        return board
    }

    class Builder{
        private var size: Int = 0
        private var players: MutableList<Player> = mutableListOf<Player>()
        private var board: Board? = null
        private var winningStrategies : MutableList<WinningStrategy> = mutableListOf<WinningStrategy>()

        fun setBoardSize(size: Int): Builder {
            this.size = size
            this.board = Board(size)
            return this
        }

        fun setPlayers(players: MutableList<Player>): Builder {
            this.players.addAll(players)
            return this
        }

        fun setWinningStrategies(winningStrategies: MutableList<WinningStrategy>): Builder {
            this.winningStrategies.addAll(winningStrategies)
            return this
        }

        fun setPlayer(player: Player): Builder {
            this.players.add(player)
            return this
        }

        fun setWinningStrategy(winningStrategy: WinningStrategy): Builder{
            this.winningStrategies.add(winningStrategy)
            return this
        }

        fun build(): Game {
            try{
                validate()
            } catch (e: Exception) {
                throw e
            }
            return Game(size, players, board!!, winningStrategies)
        }

        private fun validate() {
            try {
                validatePlayerSymbols()
                validateSingleBot()
            } catch(e: Exception) {
                throw e
            }
        }

        private fun validateSingleBot() {
            val botCount = players.filter { it.getPlayerType() == PlayerType.BOT }.count()
            if(botCount > 1) {
                throw MoreThanOneBotException()
            }
        }

        private fun validatePlayerSymbols() {
            val symbolMap = mutableMapOf<String, Int>()
            players.forEach {
                if(symbolMap.containsKey(it.getSymbol())) {
                    throw DuplicateSymbolException()
                } else {
                    symbolMap[it.getSymbol()] = 1
                }
            }
        }
    }
}