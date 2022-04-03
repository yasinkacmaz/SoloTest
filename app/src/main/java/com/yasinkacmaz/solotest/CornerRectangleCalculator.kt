package com.yasinkacmaz.solotest

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size

object CornerRectangleCalculator {
    fun BoardConfig.cornerRectangles(): List<Rect> {
        val boardStart = Offset(boardStartX, boardStartY)
        val cornerLength = cornerSize * (spacingPx + pegSize)
        val cornerSize = Size(cornerLength, cornerLength)
        val cornerStartX = boardStartX + boardRadius * 2 - cornerLength
        val cornerStartY = boardStartY + boardRadius * 2 - cornerLength
        val topLeft = Rect(offset = boardStart, size = cornerSize)
        val topRight = Rect(offset = boardStart.copy(x = cornerStartX), size = cornerSize)
        val bottomRight = Rect(offset = boardStart.copy(x = cornerStartX, y = cornerStartY), size = cornerSize)
        val bottomLeft = Rect(offset = boardStart.copy(y = cornerStartY), size = cornerSize)
        return listOf(topLeft, topRight, bottomRight, bottomLeft)
    }
}
