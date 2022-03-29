package com.yasinkacmaz.solotest

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import com.yasinkacmaz.solotest.PegOffsetValidator.isValidOffset
import com.yasinkacmaz.solotest.PegOffsetCalculator.offsetOfBoardIndex

@Composable
fun SoloTestGame(
    modifier: Modifier = Modifier,
    boardConfig: BoardConfig,
    playableBoardIndexes: MutableList<Int>,
    pegs: List<Peg>,
    selectedPegIndex: MutableState<Int?>
) = Canvas(modifier) {
    drawCircle(
        color = Color.Red,
        center = boardConfig.boardCenter,
        radius = boardConfig.boardRadius,
        style = Stroke(width = 3.dp.toPx())
    )

    playableBoardIndexes.forEach { boardIndex ->
        val peg = pegs.find { peg -> peg.boardIndex == boardIndex }
        val offset = peg?.offset ?: boardConfig offsetOfBoardIndex boardIndex
        val style = if (peg != null) Fill else Stroke(width = 1.5.dp.toPx())
        print(selectedPegIndex)
        val color = if (boardConfig.isValidOffset(offset)) Color.Green else Color.Red
        drawCircle(
            color = color,
            center = offset,
            radius = boardConfig.pegSize / 2,
            style = style
        )
        drawContext.canvas.nativeCanvas.apply {
            drawText(
                boardIndex.toString(),
                offset.x,
                offset.y + 10,
                Paint().apply {
                    textSize = 50F
                    this.color = android.graphics.Color.WHITE
                    textAlign = Paint.Align.CENTER
                }
            )
        }
    }
}
