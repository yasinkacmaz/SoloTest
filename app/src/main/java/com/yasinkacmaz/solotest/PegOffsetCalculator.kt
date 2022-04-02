package com.yasinkacmaz.solotest

import androidx.compose.ui.geometry.Offset

object PegOffsetCalculator {
    infix fun BoardConfig.offsetOfBoardIndex(boardIndex: Int): Offset {
        val (row, column) = boardIndex.toRowAndColumn(gridSize)
        val boardSize = boardRadius * 2
        val spacingPx = (boardSize - (pegSize * gridSize)) / (gridSize + 1)
        val pegX = boardStartX + (spacingPx * row) + (pegSize * row) - pegRadius
        val pegY = boardStartY + (spacingPx * column) + (pegSize * column) - pegRadius
        return Offset(pegX, pegY)
    }
}
