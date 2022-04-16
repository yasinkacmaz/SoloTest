package com.yasinkacmaz.solotest

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel
import com.yasinkacmaz.solotest.CornerRectangleCalculator.cornerRectangles
import com.yasinkacmaz.solotest.CornerTextPlacer.cornerTexts
import com.yasinkacmaz.solotest.PegFinder.boardIndexOfDraggedPeg
import com.yasinkacmaz.solotest.PegOffsetCalculator.offsetOfBoardIndex
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {

    private lateinit var boardConfig: BoardConfig
    private var draggedPegBoardIndex: Int = -1
    private val pegs = mutableStateListOf<Peg>()

    private val _gameState = MutableStateFlow(GameState())
    val gameState = _gameState.asStateFlow()

    fun createBoardConfig(canvasSize: Size, pegRadius: Float) {
        boardConfig = BoardConfig(
            boardRadius = canvasSize.minDimension / 2.2f,
            boardCenter = Offset(x = canvasSize.width / 2, y = canvasSize.height / 2),
            pegRadius = pegRadius
        )

        pegs.clear()
        pegs.addAll(boardConfig.placeableIndexes.map { boardIndex ->
            Peg(boardIndex, boardConfig.offsetOfBoardIndex(boardIndex))
        })

        _gameState.value = GameState(
            config = boardConfig,
            corners = boardConfig.cornerRectangles(),
            cornerTexts = boardConfig.cornerTexts(),
            holes = boardConfig.holes,
            pegs = pegs
        )
    }

    fun onDragStart(offset: Offset) {
        draggedPegBoardIndex = boardConfig.boardIndexOfDraggedPeg(offset)
    }

    fun onDragEnd() {
        if (draggedPegBoardIndex !in boardConfig.boardIndexes) return
        val draggedPeg = pegs.find { it.boardIndex == draggedPegBoardIndex } ?: return
        val draggedPegIndex = pegs.indexOf(draggedPeg)
        val initialBoardIndex = draggedPeg.boardIndex
        val currentBoardIndex = boardConfig.boardIndexOfDraggedPeg(draggedPeg.offset)
        val isMovementValid = if (currentBoardIndex !in boardConfig.playableIndexes) {
            false
        } else {
            PegMovementValidator.isMovementValid(
                pegs = _gameState.value.pegs,
                draggedPeg = draggedPeg,
                gridSize = boardConfig.gridSize,
                currentBoardIndex = currentBoardIndex
            )
        }

        if (isMovementValid) {
            val eatenPegBoardIndex = EatenPegFinder.boardIndexOfEatenPeg(
                boardConfig.gridSize,
                initialBoardIndex,
                currentBoardIndex
            )
            pegs.removeAll { it.boardIndex == initialBoardIndex || it.boardIndex == eatenPegBoardIndex }
            val peg = Peg(currentBoardIndex, boardConfig.offsetOfBoardIndex(currentBoardIndex))
            pegs.add(peg)
        } else {
            pegs[draggedPegIndex] = Peg(initialBoardIndex, boardConfig.offsetOfBoardIndex(initialBoardIndex))
        }
    }

    fun onDrag(dragAmount: Offset) {
        if (draggedPegBoardIndex !in boardConfig.boardIndexes) return
        val draggedPeg = pegs.find { it.boardIndex == draggedPegBoardIndex } ?: return
        val draggedPegIndex = pegs.indexOf(draggedPeg)
        pegs[draggedPegIndex] = draggedPeg.copy(offset = draggedPeg.offset + dragAmount)
    }
}
