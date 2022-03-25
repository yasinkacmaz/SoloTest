package com.yasinkacmaz.solotest

import androidx.compose.ui.geometry.Offset

data class BoardConfig(
    val gridSize: Int = 7,
    val cornerSize: Int = 2,
    val boardRadius: Float,
    val boardCenter: Offset,
    val pegSize: Float
) {
    val boardIndexes get() = (0 until gridSize * gridSize).toMutableList()

    val cornerIndexes = boardIndexes.filter { boardIndex ->
        val row = (boardIndex / gridSize) + 1
        val column = (boardIndex % gridSize) + 1
        val isRowInCorner = row <= cornerSize || (gridSize - row) < cornerSize
        val isColumnInCorner = column <= cornerSize || gridSize - column < cornerSize
        isRowInCorner && isColumnInCorner
    }

    val boardStartX = boardCenter.x - boardRadius

    val boardStartY = boardCenter.y - boardRadius
}
