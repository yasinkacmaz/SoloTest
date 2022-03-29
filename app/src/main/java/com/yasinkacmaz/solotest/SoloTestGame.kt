package com.yasinkacmaz.solotest

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.yasinkacmaz.solotest.PegOffsetCalculator.offsetOfBoardIndex
import com.yasinkacmaz.solotest.PegOffsetValidator.isValidOffset

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
        val offset = boardConfig offsetOfBoardIndex boardIndex
        drawCircle(
            color = Color.Green,
            center = offset,
            radius = boardConfig.pegSize / 2,
            style = Stroke(width = 1.5.dp.toPx())
        )
    }

    pegs.forEach { peg ->
        val offset = peg.offset
        // TODO: No need to control after snapping added
        val color = if (boardConfig.isValidOffset(offset)) Color.Green else Color.Red
        drawCircle(
            color = color,
            center = offset,
            radius = boardConfig.pegSize / 2,
            style = Fill
        )
    }
}
