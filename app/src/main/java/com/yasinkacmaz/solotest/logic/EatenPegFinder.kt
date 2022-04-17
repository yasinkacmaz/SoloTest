package com.yasinkacmaz.solotest.logic

import com.yasinkacmaz.solotest.util.Direction
import com.yasinkacmaz.solotest.util.RowAndColumn
import com.yasinkacmaz.solotest.util.toBoardIndex
import com.yasinkacmaz.solotest.util.toRowAndColumn

object EatenPegFinder {
    @Suppress("MoveVariableDeclarationIntoWhen")
    fun boardIndexOfEatenPeg(gridSize: Int, initialBoardIndex: Int, currentBoardIndex: Int): Int {
        val (initialRow, initialColumn) = initialBoardIndex.toRowAndColumn(gridSize)
        val (currentRow, currentColumn) = currentBoardIndex.toRowAndColumn(gridSize)
        val direction = directionOfMovement(initialRow to initialColumn, currentRow to currentColumn)
        val eatenPegRowAndColumn = when (direction) {
            Direction.UP -> currentRow + 1 to currentColumn
            Direction.DOWN -> currentRow - 1 to currentColumn
            Direction.LEFT -> currentRow to currentColumn + 1
            Direction.RIGHT -> currentRow to currentColumn - 1
        }

        return eatenPegRowAndColumn.toBoardIndex(gridSize)
    }

    private fun directionOfMovement(initialRowAndColumn: RowAndColumn, currentRowAndColumn: RowAndColumn): Direction {
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
