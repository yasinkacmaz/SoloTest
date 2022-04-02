package com.yasinkacmaz.solotest

import kotlin.math.abs

object PegMovementValidator {
    fun isMovementValid(gridSize: Int, pegs: List<Peg>, initialBoardIndex: Int, currentBoardIndex: Int): Boolean {
        val hasPegAtCurrentIndex = pegs.any { it.boardIndex == currentBoardIndex }
        if (hasPegAtCurrentIndex) return false

        val (initialRow, initialColumn) = initialBoardIndex.toRowAndColumn(gridSize)
        val (currentRow, currentColumn) = currentBoardIndex.toRowAndColumn(gridSize)

        val isDiagonalMovement = initialRow != currentRow && initialColumn != currentColumn
        if (isDiagonalMovement) return false

        val isMovementOutOfRange = abs(initialRow - currentRow) != 2 && abs(initialColumn - currentColumn) != 2
        if (isMovementOutOfRange) return false

        val isHorizontalMovement = initialRow == currentRow
        val isVerticalMovement = initialColumn == currentColumn

        if (isHorizontalMovement) {
            val hasPegWithinRange = pegs.any {
                val (_, pegColumn) = it.boardIndex.toRowAndColumn(gridSize)
                abs(currentColumn - pegColumn) == 1
            }
            if (!hasPegWithinRange) return false
        }

        if (isVerticalMovement) {
            val hasPegWithinRange = pegs.any {
                val (pegRow, _) = it.boardIndex.toRowAndColumn(gridSize)
                abs(currentRow - pegRow) == 1
            }
            if (!hasPegWithinRange) return false
        }
        return true
    }

    fun directionOfMovement(initialRowAndColumn: RowAndColumn, currentRowAndColumn: RowAndColumn): Direction {
        val (initialRow, initialColumn) = initialRowAndColumn
        val (currentRow, currentColumn) = currentRowAndColumn
        val isHorizontalMovement = initialRow == currentRow
        return when {
            isHorizontalMovement && currentColumn > initialColumn -> Direction.DOWN
            isHorizontalMovement && currentColumn <= initialColumn -> Direction.UP
            currentRow > initialRow -> Direction.RIGHT
            else -> Direction.LEFT
        }
    }
}
