package com.yasinkacmaz.solotest

object EatenPegFinder {
    fun boardIndexOfEatenPeg(gridSize: Int, initialBoardIndex: Int, currentBoardIndex: Int): Int {
        val (initialRow, initialColumn) = initialBoardIndex.toRowAndColumn(gridSize)
        val (currentRow, currentColumn) = currentBoardIndex.toRowAndColumn(gridSize)
        val direction =
            PegMovementValidator.directionOfMovement(initialRow to initialColumn, currentRow to currentColumn)
        val eatenPegRowAndColumn = when (direction) {
            Direction.UP -> currentRow + 1 to currentColumn
            Direction.DOWN -> currentRow - 1 to currentColumn
            Direction.LEFT -> currentRow to currentColumn + 1
            Direction.RIGHT -> currentRow to currentColumn - 1
        }

        return eatenPegRowAndColumn.toBoardIndex(gridSize)
    }
}
