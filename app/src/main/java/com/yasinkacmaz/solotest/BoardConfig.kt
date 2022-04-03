package com.yasinkacmaz.solotest

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.yasinkacmaz.solotest.PegOffsetCalculator.offsetOfBoardIndex

/*
Think of circle board as a grid
Center and corner pins are empty initially
That means there will be 32 pins initially

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
    val boardCircleThickness = 6
    val cornerLineThickness = boardCircleThickness / 2
    val boardColor = Color.Red

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

    val holes = playableIndexes.map { boardIndex -> offsetOfBoardIndex(boardIndex) }
}
