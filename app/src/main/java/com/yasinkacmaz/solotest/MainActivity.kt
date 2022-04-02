package com.yasinkacmaz.solotest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.yasinkacmaz.solotest.PegOffsetCalculator.offsetOfBoardIndex
import com.yasinkacmaz.solotest.ui.theme.SoloTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SoloTestTheme {
                BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                    val canvasSize = Size(width = maxWidth.toPx(), height = maxHeight.toPx())
                    val boardConfig = BoardConfig(
                        boardRadius = canvasSize.minDimension / 2.4f,
                        boardCenter = Offset(x = canvasSize.width / 2, y = canvasSize.height / 2),
                        pegRadius = 16.dp.toPx()
                    )
                    val playableBoardIndexes = boardConfig.boardIndexes.also { it.removeAll(boardConfig.cornerIndexes) }
                    val pegs =
                        remember { mutableStateListOf<Peg>().also { it.addAll(PegPlacer.placeToBoard(boardConfig)) } }
                    var draggedPegIndex: Int by remember { mutableStateOf(-1) }
                    val modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    val peg = PegFinder.pegAtOffset(pegs, offset, boardConfig.pegRadius)
                                    draggedPegIndex = pegs.indexOf(peg)
                                },
                                onDragEnd = {
                                    if (draggedPegIndex !in pegs.indices) return@detectDragGestures
                                    val peg = pegs[draggedPegIndex]
                                    val initialBoardIndex = peg.boardIndex
                                    val currentBoardIndex = PegPlacer.placeToClosestHole(boardConfig, peg.offset)
                                    var isMovementValid = false
                                    pegs[draggedPegIndex] = if (currentBoardIndex != null) {
                                        isMovementValid = PegMovementValidator.isMovementValid(
                                            gridSize = boardConfig.gridSize,
                                            pegs = pegs,
                                            initialBoardIndex = initialBoardIndex,
                                            currentBoardIndex = currentBoardIndex
                                        )
                                        if (isMovementValid) {
                                            val offset = boardConfig offsetOfBoardIndex currentBoardIndex
                                            peg.copy(boardIndex = currentBoardIndex, offset = offset)
                                        } else {
                                            peg.copy(offset = boardConfig offsetOfBoardIndex peg.boardIndex)
                                        }
                                    } else {
                                        peg.copy(offset = boardConfig offsetOfBoardIndex peg.boardIndex)
                                    }

                                    if (isMovementValid) {
                                        PegDeleter.deletePeg(
                                            pegs = pegs,
                                            gridSize = boardConfig.gridSize,
                                            initialBoardIndex = initialBoardIndex,
                                            currentBoardIndex = pegs[draggedPegIndex].boardIndex
                                        )
                                    }
                                }
                            ) { change, dragAmount ->
                                change.consumeAllChanges()
                                if (draggedPegIndex !in pegs.indices) return@detectDragGestures
                                val peg = pegs[draggedPegIndex]
                                val offset = peg.offset.plus(Offset(dragAmount.x, dragAmount.y))
                                pegs[draggedPegIndex] = peg.copy(offset = offset)
                            }
                        }

                    SoloTestGame(modifier, boardConfig, playableBoardIndexes, pegs)
                }
            }
        }
    }
}
