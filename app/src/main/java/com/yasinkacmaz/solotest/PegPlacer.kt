package com.yasinkacmaz.solotest

/**
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
object PegPlacer {
    fun placeToBoard(config: BoardConfig) = with(config) {
        val spacingPx = (boardSize - (pegSize * gridSize)) / (gridSize + 1)
        val center = (gridSize / 2) + 1
        val pinRadius = pegSize / 2

        buildList {
            repeat(gridSize * gridSize) { gridIndex ->
                val row = (gridIndex / gridSize) + 1
                val column = (gridIndex % gridSize) + 1
                val isRowInCorner = row <= cornerSize || (gridSize - row) < cornerSize
                val isColumnInCorner = column <= cornerSize || gridSize - column < cornerSize
                val isCenterIndex = row == center && column == center
                if (isRowInCorner && isColumnInCorner || isCenterIndex) return@repeat

                val pegX = boardX + (spacingPx * row) + (pegSize * row) - pinRadius
                val pegY = boardY + (spacingPx * column) + (column * pegSize) - pinRadius
                add(Peg(gridIndex = gridIndex, x = pegX, y = pegY))
            }
        }
    }
}
