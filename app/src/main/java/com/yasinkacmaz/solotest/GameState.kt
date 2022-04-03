package com.yasinkacmaz.solotest

import androidx.compose.ui.geometry.Offset

data class GameState(
    val config: BoardConfig = BoardConfig(),
    val corners: List<CornerRectangle> = emptyList(),
    val cornerTexts: List<CornerText> = emptyList(),
    val holes: List<Offset> = emptyList(),
    val pegs: List<Peg> = emptyList()
)
