package com.yasinkacmaz.solotest.logic

import com.yasinkacmaz.solotest.data.Peg
import com.yasinkacmaz.solotest.util.toRowAndColumn
import kotlin.math.abs

object PegMovementValidator {
    fun isMovementValid(
        pegs: List<Peg>,
        draggedPeg: Peg,
        currentBoardIndex: Int,
        gridSize: Int,
    ): Boolean {
        val hasPegAtCurrentIndex = pegs.filter { it != draggedPeg }
            .any { it.offset == draggedPeg.offset && it.boardIndex == currentBoardIndex }
        if (hasPegAtCurrentIndex) return false

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
}
