package com.yasinkacmaz.solotest

import androidx.compose.ui.geometry.Offset

object PegPositionCalculator {
    infix fun BoardConfig.positionOfIndex(boardIndex: Int): Offset {
        val row = (boardIndex / gridSize) + 1
        val column = (boardIndex % gridSize) + 1
        val pinRadius = pegSize / 2
        val spacingPx = (boardSize - (pegSize * gridSize)) / (gridSize + 1)
        val pegX = boardX + (spacingPx * row) + (pegSize * row) - pinRadius
        val pegY = boardY + (spacingPx * column) + (column * pegSize) - pinRadius
        return Offset(pegX, pegY)
    }
}
