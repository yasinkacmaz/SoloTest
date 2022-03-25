package com.yasinkacmaz.solotest

data class BoardConfig(
    val gridSize: Int = 7,
    val cornerSize: Int = 2,
    val boardSize: Float,
    val pegSize: Float,
    val boardX: Float,
    val boardY: Float
) {
    val boardIndexes get() = (0 until gridSize * gridSize).toMutableList()

    val cornerIndexes = boardIndexes.filter { boardIndex ->
        val row = (boardIndex / gridSize) + 1
        val column = (boardIndex % gridSize) + 1
        val isRowInCorner = row <= cornerSize || (gridSize - row) < cornerSize
        val isColumnInCorner = column <= cornerSize || gridSize - column < cornerSize
        isRowInCorner && isColumnInCorner
    }
}
