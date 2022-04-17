package com.yasinkacmaz.solotest

import androidx.compose.ui.geometry.Offset
import kotlin.math.absoluteValue

object PegFinder {
    fun boardIndexOfDraggedPeg(
        boardConfig: BoardConfig,
        pegOffset: Offset
    ): Int = boardConfig.boardIndexes.indexOfLast { boardIndex ->
        val offsetOfBoardIndex = PegOffsetCalculator.offsetOfBoardIndex(boardConfig, boardIndex)
        offsetOfBoardIndex.isOffsetInside(pegOffset, boardConfig.pegRadius)
    }

    private fun Offset.isOffsetInside(otherOffset: Offset, pegRadius: Float): Boolean {
        val remainingDistance = this - otherOffset
        return remainingDistance.x.absoluteValue <= pegRadius && remainingDistance.y.absoluteValue <= pegRadius
    }
}
