package com.yasinkacmaz.solotest

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect

data class GameState(
    val config: BoardConfig = BoardConfig(),
    val corners: List<Rect> = emptyList(),
    val holes: List<Offset> = emptyList(),
    val pegs: List<Peg> = emptyList()
)
