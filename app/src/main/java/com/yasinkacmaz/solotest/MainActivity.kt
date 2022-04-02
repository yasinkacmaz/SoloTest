package com.yasinkacmaz.solotest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yasinkacmaz.solotest.ui.theme.SoloTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SoloTestTheme {
                BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                    val gameViewModel: GameViewModel = viewModel()
                    if (savedInstanceState == null) {
                        val canvasSize = Size(width = maxWidth.toPx(), height = maxHeight.toPx())
                        val pegRadius = 16.dp.toPx()
                        gameViewModel.createBoardConfig(canvasSize, pegRadius)
                    }
                    val gameState = gameViewModel.gameState.collectAsState().value

                    val modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = gameViewModel::onDragStart,
                                onDragEnd = gameViewModel::onDragEnd,
                                onDrag = { change, dragAmount ->
                                    change.consumeAllChanges()
                                    gameViewModel.onDrag(dragAmount)
                                }
                            )
                        }

                    SoloTestGame(modifier, gameState)
                }
            }
        }
    }
}
