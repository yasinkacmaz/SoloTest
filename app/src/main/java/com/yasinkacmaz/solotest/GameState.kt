package com.yasinkacmaz.solotest

import androidx.compose.ui.geometry.Offset

data class GameState(
    val boardCenter: Offset = Offset.Unspecified,
    val boardRadius: Float = Float.NaN,
    val pegRadius: Float = Float.NaN,
    val holes: List<Offset> = emptyList(),
    val pegs: List<Peg> = emptyList()
)
