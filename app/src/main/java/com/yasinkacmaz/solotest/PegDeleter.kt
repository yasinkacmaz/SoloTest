package com.yasinkacmaz.solotest

import androidx.compose.runtime.snapshots.SnapshotStateList

object PegDeleter {
    fun deletePeg(
        pegs: SnapshotStateList<Peg>,
        gridSize: Int,
        initialBoardIndex: Int,
        currentBoardIndex: Int
    ) {
        val eatenPegIndex = pegs.indexOfPegWithinMovement(gridSize, initialBoardIndex, currentBoardIndex)
        if (eatenPegIndex in pegs.indices) {
            pegs.removeAt(eatenPegIndex)
        }
    }

    private fun List<Peg>.indexOfPegWithinMovement(gridSize: Int, initialBoardIndex: Int, currentBoardIndex: Int): Int {
        val (initialRow, initialColumn) = initialBoardIndex.toRowAndColumn(gridSize)
        val (currentRow, currentColumn) = currentBoardIndex.toRowAndColumn(gridSize)
        val direction =
            PegMovementValidator.directionOfMovement(initialRow to initialColumn, currentRow to currentColumn)

        return indexOfFirst {
            val eatenPegRowAndColumn = when (direction) {
                Direction.UP -> currentRow + 1 to currentColumn
                Direction.DOWN -> currentRow - 1 to currentColumn
                Direction.LEFT -> currentRow to currentColumn + 1
                Direction.RIGHT -> currentRow to currentColumn - 1
            }
            eatenPegRowAndColumn.toBoardIndex(gridSize) == it.boardIndex
        }
    }
}
