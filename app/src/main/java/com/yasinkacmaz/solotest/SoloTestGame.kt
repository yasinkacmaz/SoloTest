package com.yasinkacmaz.solotest

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import com.yasinkacmaz.solotest.PegFinder.isOffsetInside

@Composable
fun SoloTestGame(modifier: Modifier = Modifier, gameState: GameState) = Canvas(modifier) {
    drawCircle(
        color = Color.Red,
        center = gameState.boardCenter,
        radius = gameState.boardRadius,
        style = Stroke(width = 3.dp.toPx())
    )

    drawContext.canvas.nativeCanvas.apply {
        drawText(
            "Remaining: ${gameState.pegs.size}",
            140F,
            140F,
            Paint().apply {
                textSize = 80F
                this.color = android.graphics.Color.RED
                textAlign = Paint.Align.LEFT
            }
        )
    }

    gameState.holes.forEach { holeOffset ->
        drawCircle(
            color = Color.Green,
            center = holeOffset,
            radius = gameState.pegRadius,
            style = Stroke(width = 1.5.dp.toPx())
        )
    }

    gameState.pegs.forEach { peg ->
        val pegOffset = peg.offset
        val isValidOffset =
            gameState.holes.any { holeOffset -> holeOffset.isOffsetInside(pegOffset, gameState.pegRadius) }

        val color = if (isValidOffset) Color.Green else Color.Red
        drawCircle(
            color = color,
            center = pegOffset,
            radius = gameState.pegRadius,
            style = Fill
        )
    }
}
