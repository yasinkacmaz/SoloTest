package com.yasinkacmaz.solotest

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yasinkacmaz.solotest.CornerRectangleCalculator.cornerRectangles
import com.yasinkacmaz.solotest.CornerTextPlacer.cornerTexts
import com.yasinkacmaz.solotest.PegFinder.boardIndexOfDraggedPeg
import com.yasinkacmaz.solotest.PegOffsetCalculator.offsetOfBoardIndex

class GameViewModel(canvasSize: Size, pegRadius: Float) : ViewModel() {

    private val boardConfig: BoardConfig = BoardConfig(
        boardRadius = canvasSize.minDimension / 2.2f,
        boardCenter = Offset(x = canvasSize.width / 2, y = canvasSize.height / 2),
        pegRadius = pegRadius
    )
    private var draggedPegBoardIndex: Int = -1
    val pegs = mutableStateListOf<Peg>()

    init {
        initPegs()
    }

    private val _gameConfig = mutableStateOf(
        GameConfig(
            boardConfig = boardConfig,
            corners = boardConfig.cornerRectangles(),
            cornerTexts = boardConfig.cornerTexts(),
            holes = boardConfig.holes
        )
    )
    val gameConfig: State<GameConfig> = _gameConfig

    val gameOver by lazy {
        derivedStateOf {
            GameOverDetector.isGameOver(pegs, boardConfig.gridSize)
        }
    }

    fun onDragStart(offset: Offset) {
        draggedPegBoardIndex = boardConfig.boardIndexOfDraggedPeg(offset)
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
        val currentBoardIndex = boardConfig.boardIndexOfDraggedPeg(draggedPeg.offset)
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
            val peg = Peg(currentBoardIndex, boardConfig.offsetOfBoardIndex(currentBoardIndex))
            pegs.add(peg)
        } else {
            pegs[draggedPegIndex] = Peg(initialBoardIndex, boardConfig.offsetOfBoardIndex(initialBoardIndex))
        }
    }

    fun onPlayAgainClicked() {
        initPegs()
    }

    private fun initPegs() {
        pegs.clear()
        pegs.addAll(boardConfig.placeableIndexes.map { boardIndex ->
            Peg(boardIndex, boardConfig.offsetOfBoardIndex(boardIndex))
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
