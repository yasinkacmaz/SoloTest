package com.yasinkacmaz.solotest.data

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yasinkacmaz.solotest.logic.PegOffsetCalculator
import com.yasinkacmaz.solotest.util.toRowAndColumn

/*
This is English style Peg Solitaire board
Think of circle board as a grid
Center and corner holes are empty initially
That means there will be 32 pegs initially

00 01 02 03 04 05 06
07 08 09 10 11 12 13
14 15 16 17 18 19 20
21 22 23 24 25 26 27
28 29 30 31 32 33 34
35 36 37 38 39 40 41
42 43 44 45 46 47 48

      01 02 03
      04 05 06
07 08 09 10 11 12 13
14 15 16 XX 17 18 19
20 21 22 23 24 25 26
      27 28 29
      30 31 32
*/
data class BoardConfig(
    val gridSize: Int = 7,
    val cornerSize: Int = 2,
    val boardRadius: Float = Float.NaN,
    val boardCenter: Offset = Offset.Zero,
    val pegRadius: Float = Float.NaN
) {
    val boardStartX = boardCenter.x - boardRadius
    val boardStartY = boardCenter.y - boardRadius
    val pegSize = pegRadius * 2
    val spacingPx = (boardRadius * 2 - (pegSize * gridSize)) / (gridSize + 1)
    val boardCircleThickness = 6.dp
    val cornerLineThickness = boardCircleThickness / 2
    val holeThickness = boardCircleThickness / 2
    val cornerTextSize = 15.sp
    val boardColor = Color(0xFF0D47A1) // Blue900
    val pegColor = Color(0xFFFBC02D) // Yellow700
    val pegGradientColors = listOf(pegColor, Color(0xFF3D3317))
    val boardBackgroundColor = boardColor.copy(alpha = 0.7f)

    val boardIndexes get() = (0 until gridSize * gridSize).toMutableList()

    private val cornerIndexes = boardIndexes.filter { boardIndex ->
        val (row, column) = boardIndex.toRowAndColumn(gridSize)
        val isRowInCorner = row <= cornerSize || (gridSize - row) < cornerSize
        val isColumnInCorner = column <= cornerSize || gridSize - column < cornerSize
        isRowInCorner && isColumnInCorner
    }
    val playableIndexes = boardIndexes - cornerIndexes

    val placeableIndexes = boardIndexes.also {
        it.remove(it.size / 2) // remove center index
        it.removeAll(cornerIndexes)
    }

    val holes = playableIndexes.map { boardIndex -> PegOffsetCalculator.offsetOfBoardIndex(this, boardIndex) }
}
