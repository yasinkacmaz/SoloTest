package com.yasinkacmaz.solotest

import kotlin.math.abs

object PegMovementValidator {
    fun isMovementValid(
        pegs: List<Peg>,
        draggedPeg: Peg,
        currentBoardIndex: Int,
        gridSize: Int,
    ): Boolean {
        val hasPegAtCurrentIndex = pegs.filter { it != draggedPeg }.find {
            it.offset == draggedPeg.offset && it.boardIndex == currentBoardIndex
        }
        if (hasPegAtCurrentIndex != null) return false

        val (initialRow, initialColumn) = draggedPeg.boardIndex.toRowAndColumn(gridSize)
        val (currentRow, currentColumn) = currentBoardIndex.toRowAndColumn(gridSize)

        val isDiagonalMovement = initialRow != currentRow && initialColumn != currentColumn
        if (isDiagonalMovement) return false

        val isMovementOutOfRange = abs(initialRow - currentRow) != 2 && abs(initialColumn - currentColumn) != 2
        if (isMovementOutOfRange) return false

        val eatenPegBoardIndex = EatenPegFinder.boardIndexOfEatenPeg(gridSize, draggedPeg.boardIndex, currentBoardIndex)
        val hasPegWithinRange = pegs.any { peg -> peg.boardIndex == eatenPegBoardIndex }
        if (!hasPegWithinRange) return false

        return true
    }

    fun directionOfMovement(initialRowAndColumn: RowAndColumn, currentRowAndColumn: RowAndColumn): Direction {
        val (initialRow, initialColumn) = initialRowAndColumn
        val (currentRow, currentColumn) = currentRowAndColumn
        val isHorizontalMovement = initialRow == currentRow
        return when {
            isHorizontalMovement && currentColumn > initialColumn -> Direction.RIGHT
            isHorizontalMovement && currentColumn <= initialColumn -> Direction.LEFT
            currentRow > initialRow -> Direction.DOWN
            else -> Direction.UP
        }
    }
}
