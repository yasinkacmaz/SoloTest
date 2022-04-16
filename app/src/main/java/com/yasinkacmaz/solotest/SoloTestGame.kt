package com.yasinkacmaz.solotest

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb

@Composable
fun SoloTestGame(modifier: Modifier = Modifier, gameState: GameState) = Canvas(modifier) {
    val config = gameState.config

    val boardPath = Path().apply {
        addOval(Rect(center = config.boardCenter, radius = config.boardRadius))
    }

    clipPath(boardPath, ClipOp.Intersect) {
        drawPath(
            path = boardPath,
            color = config.boardBackgroundColor,
            style = Fill,
            blendMode = BlendMode.SrcAtop
        )

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

        gameState.cornerTexts.forEach { cornerText ->
            rotate(cornerText.rotateDegree, pivot = cornerText.textStart) {
                drawContext.canvas.nativeCanvas.drawText(
                    cornerText.text,
                    cornerText.textStart.x,
                    cornerText.textStart.y,
                    Paint().apply {
                        textSize = config.cornerTextSize.toPx()
                        color = config.boardColor.toArgb()
                        isFakeBoldText = true
                        textAlign = cornerText.textAlign
                    }
                )
            }
        }
    }

    val holeThicknessPx = config.holeThickness.toPx()
    gameState.holes.forEach { holeOffset ->
        drawCircle(
            color = config.boardColor,
            center = holeOffset,
            radius = config.pegRadius,
            style = Stroke(width = holeThicknessPx)
        )
    }

    gameState.pegs.forEach { peg ->
        drawCircle(
            color = config.pegColor,
            center = peg.offset,
            radius = config.pegRadius - holeThicknessPx / 2
        )
    }
}
