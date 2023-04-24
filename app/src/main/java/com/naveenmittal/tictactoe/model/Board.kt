package com.naveenmittal.tictactoe.model

class Board(val size: Int) {
    private val board: List<List<Cell>> = initializeBoard(size)

    private fun initializeBoard(size: Int): List<List<Cell>> {
        val board = mutableListOf<List<Cell>>()
        for (i in 0 until size) {
            val row = mutableListOf<Cell>()
            for (j in 0 until size) {
                row.add(Cell())
            }
            board.add(row)
        }
        return board
    }

    fun makeMove(move: Move) {
        val row = move.row
        val col = move.col
        val player = move.player

        val cell = board[row][col]
        if (cell.isEmpty()) {
            cell.player = player
            cell.setCellPlayer(player)
            cell.setCellState(CellState.OCCUPIED)
        } else {
            throw Exception("Invalid move")
        }
    }

    fun printBoard() {
        for (i in 0 until size) {
            for (j in 0 until size) {
                print(board[i][j].player?.getSymbol() ?: "-")
                print(" ")
            }
            println()
        }
    }
}