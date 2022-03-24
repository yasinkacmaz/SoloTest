package com.yasinkacmaz.solotest

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun SoloTestGame() = Canvas(modifier = Modifier.fillMaxSize()) {
    val canvasWidth = size.width
    val canvasHeight = size.height
    val boardCenter = Offset(x = canvasWidth / 2, y = canvasHeight / 2)
    val boardRadius = size.minDimension / 2.4f
    val pegRadius = 12.dp.toPx()

    drawCircle(
        color = Color.Red,
        center = boardCenter,
        radius = size.minDimension / 2.4f,
        style = Stroke(width = 1.dp.toPx())
    )

    val boardConfig = BoardConfig(
        boardSize = boardRadius * 2,
        pegSize = pegRadius * 2,
        boardX = boardCenter.x - boardRadius,
        boardY = boardCenter.y - boardRadius
    )
    val pegs = PegPlacer.placeToBoard(boardConfig)
    pegs.forEach { peg ->
        drawCircle(
            color = Color.Blue,
            center = Offset(x = peg.x, y = peg.y),
            radius = pegRadius,
            style = Stroke(width = 1.dp.toPx())
        )
    }
}
