package com.yasinkacmaz.solotest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
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
                        remember { mutableStateListOf<Peg>() }.also { it.addAll(PegPlacer.placeToBoard(boardConfig)) }
                    var offsetX by remember { mutableStateOf(0f) }
                    var offsetY by remember { mutableStateOf(0f) }
                    var draggedPeg: Peg? by remember { mutableStateOf(null) }
                    val modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    offsetX = 0f
                                    offsetY = 0f
                                    draggedPeg = PegFinder.pegAtOffset(pegs, offset, boardConfig.pegRadius)
                                }
                            ) { change, dragAmount ->
                                change.consumeAllChanges()
                                offsetX += dragAmount.x
                                offsetY += dragAmount.y
                                val peg = draggedPeg ?: return@detectDragGestures
                                val index = pegs.indexOfFirst { it.boardIndex == peg.boardIndex }
                                if (index !in pegs.indices) return@detectDragGestures
                                pegs[index] = peg.copy(offset = peg.offset.plus(Offset(offsetX, offsetY)))
                            }
                        }
                    val selectedPegIndex: MutableState<Int?> = remember { mutableStateOf(null) }

                    SoloTestGame(modifier, boardConfig, playableBoardIndexes, pegs, selectedPegIndex)
                }
            }
        }
    }
}
