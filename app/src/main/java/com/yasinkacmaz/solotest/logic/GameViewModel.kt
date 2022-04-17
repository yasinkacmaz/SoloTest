package com.yasinkacmaz.solotest.logic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yasinkacmaz.solotest.data.BoardConfig
import com.yasinkacmaz.solotest.data.GameConfig
import com.yasinkacmaz.solotest.data.Peg
import com.yasinkacmaz.solotest.data.Remaining

class GameViewModel(canvasSize: Size, pegRadius: Float) : ViewModel() {

    private val boardConfig = BoardConfig(
        boardRadius = canvasSize.minDimension / 2.2f,
        boardCenter = Offset(x = canvasSize.width / 2, y = canvasSize.height / 2),
        pegRadius = pegRadius
    )
    val gameConfig = GameConfig(
        boardConfig = boardConfig,
        corners = CornerRectangleCalculator.cornerRectangles(boardConfig),
        cornerTexts = CornerTextPlacer.cornerTexts(boardConfig),
        holes = boardConfig.holes
    )

    private var draggedPegBoardIndex: Int = -1
    val pegs = mutableStateListOf<Peg>()

    val isGameOver
        @Composable
        get() = remember(pegs.size) { GameOverDetector.isGameOver(pegs, gameConfig.boardConfig.gridSize) }

    val remaining
        @Composable
        get() = remember(pegs.size) { Remaining.of(pegs.size) }

    init {
        initPegs()
    }

    fun onDragStart(offset: Offset) {
        draggedPegBoardIndex = PegFinder.boardIndexOfDraggedPeg(boardConfig, offset)
    }

    fun onDrag(dragAmount: Offset) {
        if (draggedPegBoardIndex !in boardConfig.boardIndexes) return
        val draggedPeg = pegs.find { it.boardIndex == draggedPegBoardIndex } ?: return

        val draggedPegIndex = pegs.indexOf(draggedPeg)
        pegs[draggedPegIndex] = draggedPeg.copy(offset = draggedPeg.offset + dragAmount)
    }

    fun onDragEnd() {
        if (draggedPegBoardIndex !in boardConfig.boardIndexes) return
        val draggedPeg = pegs.find { it.boardIndex == draggedPegBoardIndex } ?: return
        val draggedPegIndex = pegs.indexOf(draggedPeg)
        val initialBoardIndex = draggedPeg.boardIndex
        val currentBoardIndex = PegFinder.boardIndexOfDraggedPeg(boardConfig, draggedPeg.offset)
        val isMovementValid = if (currentBoardIndex !in boardConfig.playableIndexes) {
            false
        } else {
            PegMovementValidator.isMovementValid(
                pegs = pegs,
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
            val peg = Peg(currentBoardIndex, PegOffsetCalculator.offsetOfBoardIndex(boardConfig, currentBoardIndex))
            pegs.add(peg)
        } else {
            pegs[draggedPegIndex] =
                Peg(initialBoardIndex, PegOffsetCalculator.offsetOfBoardIndex(boardConfig, initialBoardIndex))
        }
    }

    fun onPlayAgainClicked() {
        initPegs()
    }

    private fun initPegs() {
        pegs.clear()
        pegs.addAll(boardConfig.placeableIndexes.map { boardIndex ->
            Peg(boardIndex, PegOffsetCalculator.offsetOfBoardIndex(boardConfig, boardIndex))
        })
    }
}

@Suppress("UNCHECKED_CAST")
class GameViewModelFactory(
    private val canvasSize: Size,
    private val pegRadius: Float
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = GameViewModel(canvasSize, pegRadius) as T
}
