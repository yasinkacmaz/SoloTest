package com.yasinkacmaz.solotest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val screenWidth = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.toPx() }
            val screenHeight = with(LocalDensity.current) { LocalConfiguration.current.screenHeightDp.dp.toPx() / 2 }
            val canvasSize = Size(screenWidth, screenHeight)
            val pegRadius = LocalDensity.current.run { 18.dp.toPx() }
            val gameViewModel: GameViewModel = viewModel(factory = GameViewModelFactory(canvasSize, pegRadius))

            val backgroundColor = if (isSystemInDarkTheme()) Color(0xFF1C1C1C) else Color.White
            val modifier = Modifier
                .fillMaxSize()
                .then(Modifier.background(backgroundColor))
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

            val gameConfig = remember { gameViewModel.gameConfig.value }
            val pegs = remember { gameViewModel.pegs }
            Column(verticalArrangement = Arrangement.Center) {
                SoloTestGameRemaining(
                    Modifier
                        .weight(0.4f)
                        .background(backgroundColor),
                    gameConfig.boardConfig.boardColor,
                    pegs.size
                )
                SoloTestGame(modifier.weight(0.6f), gameConfig, gameViewModel.pegs)
            }

            val gameOver = remember { gameViewModel.gameOver.value }
            if (gameOver) {
                // dialog
            }
        }
    }
}
