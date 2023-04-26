package com.naveenmittal.tictactoe.model

class Cell {
    private var cellState: CellState = CellState.EMPTY
    var player: Player? = null

    fun isEmpty(): Boolean {
        return cellState == CellState.EMPTY
    }

    fun setCellPlayer(player: Player?) {
        this.player = player
        cellState = CellState.OCCUPIED
    }

    fun setCellState(cellState: CellState) {
        this.cellState = cellState
    }
}