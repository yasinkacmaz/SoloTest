package com.yasinkacmaz.solotest

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.yasinkacmaz.solotest.PegFinder.isOffsetInside

@Composable
fun SoloTestGame(modifier: Modifier = Modifier, gameState: GameState) = Canvas(modifier) {
    val config = gameState.config

    val boardPath = Path().apply {
        addOval(Rect(center = config.boardCenter, radius = config.boardRadius))
    }

    clipPath(boardPath, clipOp = ClipOp.Intersect) {
        drawCircle(
            color = config.boardColor,
            center = config.boardCenter,
            radius = config.boardRadius,
            style = Stroke(width = config.boardCircleThickness.toPx())
        )
        gameState.corners.forEach { corner ->
            drawRect(
                color = config.boardColor,
                topLeft = corner.rect.topLeft,
                size = corner.rect.size,
                style = Stroke(width = config.cornerLineThickness.toPx())
            )
        }

        val cornerTextPaint = Paint().apply {
            textSize = config.cornerTextSize.toPx()
            color = config.boardColor.toArgb()
            isFakeBoldText = true
        }
        gameState.cornerTexts.forEach { cornerText ->
            rotate(cornerText.rotateDegree, pivot = cornerText.textStart) {
                drawContext.canvas.nativeCanvas.drawText(
                    cornerText.text,
                    cornerText.textStart.x,
                    cornerText.textStart.y,
                    cornerTextPaint.apply { textAlign = cornerText.textAlign }
                )
            }
        }
    }

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
            radius = config.pegRadius,
            style = Stroke(width = 1.5.dp.toPx())
        )
    }

    gameState.pegs.forEach { peg ->
        val pegOffset = peg.offset
        val isValidOffset = gameState.holes.any { holeOffset -> holeOffset.isOffsetInside(pegOffset, config.pegRadius) }

        val color = if (isValidOffset) Color.Green else Color.Red
        drawCircle(
            color = color,
            center = pegOffset,
            radius = config.pegRadius,
            style = Fill
        )
    }
}
