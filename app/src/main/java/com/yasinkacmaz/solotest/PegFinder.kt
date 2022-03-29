package com.yasinkacmaz.solotest

import androidx.compose.ui.geometry.Offset
import kotlin.math.absoluteValue

object PegFinder {
    fun pegAtOffset(pegs: List<Peg>, offset: Offset, pegRadius: Float): Peg? = pegs.firstOrNull { peg ->
        peg.offset.isOffsetInside(offset, pegRadius)
    }

    fun Offset.isOffsetInside(otherOffset: Offset, pegRadius: Float): Boolean {
        val remainingDistance = this - otherOffset
        return remainingDistance.x.absoluteValue <= pegRadius && remainingDistance.y.absoluteValue <= pegRadius
    }
}
