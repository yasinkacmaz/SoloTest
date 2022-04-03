package com.yasinkacmaz.solotest

import androidx.compose.ui.geometry.Offset

object PegOffsetCalculator {
    infix fun BoardConfig.offsetOfBoardIndex(boardIndex: Int): Offset {
        val (row, column) = boardIndex.toRowAndColumn(gridSize)
        val pegX = boardStartX + (spacingPx * column) + (pegSize * column) - pegRadius
        val pegY = boardStartY + (spacingPx * row) + (pegSize * row) - pegRadius
        return Offset(pegX, pegY)
    }
}
