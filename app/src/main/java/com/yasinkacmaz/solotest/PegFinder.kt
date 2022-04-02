package com.yasinkacmaz.solotest

import androidx.compose.ui.geometry.Offset
import com.yasinkacmaz.solotest.PegOffsetCalculator.offsetOfBoardIndex
import kotlin.math.absoluteValue

object PegFinder {
    infix fun BoardConfig.boardIndexOfDraggedPeg(pegOffset: Offset): Int = boardIndexes.indexOfLast { boardIndex ->
        val offsetOfBoardIndex = offsetOfBoardIndex(boardIndex)
        offsetOfBoardIndex.isOffsetInside(pegOffset, pegRadius)
    }

    fun Offset.isOffsetInside(otherOffset: Offset, pegRadius: Float): Boolean {
        val remainingDistance = this - otherOffset
        return remainingDistance.x.absoluteValue <= pegRadius && remainingDistance.y.absoluteValue <= pegRadius
    }
}
