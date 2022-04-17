package com.yasinkacmaz.solotest

import android.graphics.Paint.Align.LEFT
import android.graphics.Paint.Align.RIGHT
import androidx.compose.ui.geometry.Offset
import com.yasinkacmaz.solotest.Corner.BOTTOM_LEFT
import com.yasinkacmaz.solotest.Corner.BOTTOM_RIGHT
import com.yasinkacmaz.solotest.Corner.TOP_LEFT
import com.yasinkacmaz.solotest.Corner.TOP_RIGHT
import com.yasinkacmaz.solotest.CornerRectangleCalculator.cornerRectangles

object CornerTextPlacer {
    private const val SOLO = "SOLO"
    private const val TEST = "TEST"

    fun cornerTexts(boardConfig: BoardConfig): List<CornerText> = cornerRectangles(boardConfig).map { (corner, rect) ->
        val bottomRight = rect.bottomRight
        val bottomLeft = rect.bottomLeft
        val topLeft = rect.topLeft
        val topRight = rect.topRight
        val padding = boardConfig.pegRadius
        when (corner) {
            TOP_LEFT -> CornerText(SOLO, bottomRight - Offset(padding, padding / 2), 45f, RIGHT)
            TOP_RIGHT -> CornerText(SOLO, bottomLeft + Offset(padding, -padding / 2), -45f, LEFT)
            BOTTOM_RIGHT -> CornerText(TEST, topLeft + Offset(padding / 2, padding), 45f, LEFT)
            BOTTOM_LEFT -> CornerText(TEST, topRight + Offset(-padding / 2, padding), -45f, RIGHT)
        }
    }
}
