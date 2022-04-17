package com.yasinkacmaz.solotest

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import com.yasinkacmaz.solotest.Corner.BOTTOM_LEFT
import com.yasinkacmaz.solotest.Corner.BOTTOM_RIGHT
import com.yasinkacmaz.solotest.Corner.TOP_LEFT
import com.yasinkacmaz.solotest.Corner.TOP_RIGHT

object CornerRectangleCalculator {
    fun cornerRectangles(boardConfig: BoardConfig): List<CornerRectangle> = with(boardConfig) {
        val boardStart = Offset(boardStartX, boardStartY)
        val cornerLength = cornerSize * (spacingPx + pegSize)
        val cornerSize = Size(cornerLength, cornerLength)
        val cornerStartX = boardStartX + boardRadius * 2 - cornerLength
        val cornerStartY = boardStartY + boardRadius * 2 - cornerLength

        Corner.values.map { corner ->
            val rect = when(corner) {
                TOP_LEFT -> Rect(offset = boardStart, size = cornerSize)
                TOP_RIGHT -> Rect(offset = boardStart.copy(x = cornerStartX), size = cornerSize)
                BOTTOM_RIGHT ->  Rect(offset = boardStart.copy(x = cornerStartX, y = cornerStartY), size = cornerSize)
                BOTTOM_LEFT -> Rect(offset = boardStart.copy(y = cornerStartY), size = cornerSize)
            }
            CornerRectangle(corner = corner, rect = rect)
        }
    }
}
