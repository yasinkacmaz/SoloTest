package com.yasinkacmaz.solotest

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb

@Composable
fun SoloTestGame(modifier: Modifier = Modifier, gameConfig: GameConfig, pegs: List<Peg>) = Canvas(modifier) {
    val boardConfig = gameConfig.boardConfig

    val boardPath = Path().apply {
        addOval(Rect(center = boardConfig.boardCenter, radius = boardConfig.boardRadius))
    }

    clipPath(boardPath, ClipOp.Intersect) {
        drawPath(
            path = boardPath,
            color = boardConfig.boardBackgroundColor,
            style = Fill,
            blendMode = BlendMode.SrcAtop
        )

        drawCircle(
            color = boardConfig.boardColor,
            center = boardConfig.boardCenter,
            radius = boardConfig.boardRadius,
            style = Stroke(width = boardConfig.boardCircleThickness.toPx())
        )

        gameConfig.corners.forEach { corner ->
            drawRect(
                color = boardConfig.boardColor,
                topLeft = corner.rect.topLeft,
                size = corner.rect.size,
                style = Stroke(width = boardConfig.cornerLineThickness.toPx())
            )
        }

        gameConfig.cornerTexts.forEach { cornerText ->
            rotate(cornerText.rotateDegree, pivot = cornerText.textStart) {
                drawContext.canvas.nativeCanvas.drawText(
                    cornerText.text,
                    cornerText.textStart.x,
                    cornerText.textStart.y,
                    Paint().apply {
                        textSize = boardConfig.cornerTextSize.toPx()
                        color = boardConfig.boardColor.toArgb()
                        isFakeBoldText = true
                        textAlign = cornerText.textAlign
                    }
                )
            }
        }
    }

    val holeThicknessPx = boardConfig.holeThickness.toPx()
    gameConfig.holes.forEach { holeOffset ->
        drawCircle(
            color = boardConfig.boardColor,
            center = holeOffset,
            radius = boardConfig.pegRadius,
            style = Stroke(width = holeThicknessPx)
        )
    }

    pegs.forEach { peg ->
        val gradient = linearGradient(
            colors = boardConfig.pegGradientColors,
            start = peg.offset - Offset(boardConfig.pegRadius, boardConfig.pegRadius),
            end = peg.offset + Offset(boardConfig.pegRadius, boardConfig.pegRadius),
            tileMode = TileMode.Clamp
        )
        drawCircle(
            brush = gradient,
            center = peg.offset,
            radius = boardConfig.pegRadius - holeThicknessPx / 2
        )
    }
}
