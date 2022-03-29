package com.yasinkacmaz.solotest

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
object PegPlacer {
    fun placeToBoard(config: BoardConfig) = with(config) {
        val placeableBoardIndexes = config.boardIndexes.also {
            it.remove(it.size / 2) // remove center index
            it.removeAll(config.cornerIndexes)
        }
        placeableBoardIndexes.map { boardIndex ->
            Peg(boardIndex = boardIndex, offset = config.offsetOfBoardIndex(boardIndex))
        }
    }
}
