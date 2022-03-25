package com.yasinkacmaz.solotest

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.yasinkacmaz.solotest.PegPositionCalculator.positionOfIndex

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
        style = Stroke(width = 3.dp.toPx())
    )

    val boardConfig = BoardConfig(
        boardSize = boardRadius * 2,
        pegSize = pegRadius * 2,
        boardX = boardCenter.x - boardRadius,
        boardY = boardCenter.y - boardRadius
    )
    val playableBoardIndexes = boardConfig.boardIndexes.also { it.removeAll(boardConfig.cornerIndexes) }
    val pegs = PegPlacer.placeToBoard(boardConfig)

    playableBoardIndexes.forEach { boardIndex ->
        val hasPeg = pegs.any { peg -> peg.boardIndex == boardIndex }
        val offset = boardConfig positionOfIndex boardIndex
        val style = if (hasPeg) Fill else Stroke(width = 1.5.dp.toPx())
        drawCircle(
            color = Color.Blue,
            center = offset,
            radius = pegRadius,
            style = style
        )
    }
}
