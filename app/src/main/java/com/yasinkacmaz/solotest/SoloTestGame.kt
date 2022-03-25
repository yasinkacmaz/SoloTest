package com.yasinkacmaz.solotest

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.yasinkacmaz.solotest.PegPositionCalculator.positionOfIndex

@Composable
fun SoloTestGame(
    modifier: Modifier = Modifier,
    boardConfig: BoardConfig,
    playableBoardIndexes: MutableList<Int>,
    pegs: List<Peg>
) = Canvas(modifier) {
    drawCircle(
        color = Color.Red,
        center = boardConfig.boardCenter,
        radius = boardConfig.boardRadius,
        style = Stroke(width = 3.dp.toPx())
    )

    playableBoardIndexes.forEach { boardIndex ->
        val hasPeg = pegs.any { peg -> peg.boardIndex == boardIndex }
        val offset = boardConfig positionOfIndex boardIndex
        val style = if (hasPeg) Fill else Stroke(width = 1.5.dp.toPx())
        drawCircle(
            color = Color.Blue,
            center = offset,
            radius = boardConfig.pegSize / 2,
            style = style
        )
    }
}
