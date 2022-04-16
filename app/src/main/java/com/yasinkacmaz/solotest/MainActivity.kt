package com.yasinkacmaz.solotest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SoloTestTheme {
                BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                    val gameViewModel: GameViewModel = viewModel()
                    if (savedInstanceState == null) {
                        val canvasSize = Size(width = maxWidth.toPx(), height = maxHeight.toPx() / 2)
                        val pegRadius = 18.dp.toPx()
                        gameViewModel.createBoardConfig(canvasSize, pegRadius)
                    }

                    val modifier = Modifier
                        .fillMaxSize()
                        .then(Modifier.background(MaterialTheme.colors.surface))
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

                    val gameState = gameViewModel.gameState.collectAsState().value
                    Column(verticalArrangement = Arrangement.Center) {
                        SoloTestGameRemaining(Modifier.weight(1f), gameState)
                        SoloTestGame(modifier.weight(1f), gameState)
                    }
                }
            }
        }
    }
}
