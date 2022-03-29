package com.yasinkacmaz.solotest

import androidx.compose.ui.geometry.Offset
import com.yasinkacmaz.solotest.PegFinder.isOffsetInside
import com.yasinkacmaz.solotest.PegOffsetCalculator.offsetOfBoardIndex

object PegOffsetValidator {
    infix fun BoardConfig.isValidOffset(pegOffset: Offset) = playableIndexes.any { boardIndex ->
        val offset = offsetOfBoardIndex(boardIndex)
        offset.isOffsetInside(pegOffset, pegRadius)
    }
}
