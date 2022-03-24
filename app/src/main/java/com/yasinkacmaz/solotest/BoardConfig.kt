package com.yasinkacmaz.solotest

data class BoardConfig(
    val gridSize: Int = 7,
    val cornerSize: Int = 2,
    val boardSize: Float,
    val pegSize: Float,
    val boardX: Float,
    val boardY: Float
)
