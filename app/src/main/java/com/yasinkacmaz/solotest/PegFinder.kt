package com.yasinkacmaz.solotest

import androidx.compose.ui.geometry.Offset
import kotlin.math.absoluteValue

object PegFinder {
    fun pegAtOffset(pegs: List<Peg>, offset: Offset, pegRadius: Float): Peg? = pegs.firstOrNull { peg ->
        val rem = peg.offset - offset
        rem.x.absoluteValue <= pegRadius && rem.y.absoluteValue <= pegRadius
    }
}
