package com.yasinkacmaz.solotest.util

typealias RowAndColumn = Pair<Int, Int>

fun Int.toRowAndColumn(gridSize: Int): RowAndColumn {
    val row = (this / gridSize) + 1
    val column = (this % gridSize) + 1
    return row to column
}

fun RowAndColumn.toBoardIndex(gridSize: Int): Int {
    val row = first - 1
    val column = second - 1

    return (gridSize * row) + column
}
