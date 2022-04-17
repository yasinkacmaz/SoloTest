package com.yasinkacmaz.solotest.logic

import androidx.compose.ui.geometry.Offset
import com.yasinkacmaz.solotest.data.BoardConfig
import com.yasinkacmaz.solotest.util.toRowAndColumn

object PegOffsetCalculator {
    fun offsetOfBoardIndex(boardConfig: BoardConfig, boardIndex: Int): Offset = with(boardConfig) {
        val (row, column) = boardIndex.toRowAndColumn(gridSize)
        val pegX = boardStartX + (spacingPx * column) + (pegSize * column) - pegRadius
        val pegY = boardStartY + (spacingPx * row) + (pegSize * row) - pegRadius
        return Offset(pegX, pegY)
    }
}
