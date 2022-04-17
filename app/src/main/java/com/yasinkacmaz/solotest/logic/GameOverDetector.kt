package com.yasinkacmaz.solotest.logic

import com.yasinkacmaz.solotest.util.Direction
import com.yasinkacmaz.solotest.data.Peg
import com.yasinkacmaz.solotest.data.Remaining
import com.yasinkacmaz.solotest.util.toBoardIndex
import com.yasinkacmaz.solotest.util.toRowAndColumn

object GameOverDetector {
    fun isGameOver(pegs: List<Peg>, gridSize: Int): Boolean {
        if (Remaining.of(pegs.size) == null) return false

        return pegs.none { peg ->
            val (row, column) = peg.boardIndex.toRowAndColumn(gridSize)
            Direction.values.any { direction ->
                val movedPlace = when (direction) {
                    Direction.UP -> row - 2 to column
                    Direction.DOWN -> row + 2 to column
                    Direction.LEFT -> row to column - 2
                    Direction.RIGHT -> row to column + 2
                }
                PegMovementValidator.isMovementValid(pegs, peg, movedPlace.toBoardIndex(gridSize), gridSize)
            }
        }
    }
}
