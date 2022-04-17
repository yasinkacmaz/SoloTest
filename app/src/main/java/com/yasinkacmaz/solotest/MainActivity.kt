package com.yasinkacmaz.solotest

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
            val configuration = LocalConfiguration.current
            val density = LocalDensity.current
            val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
            val canvasSize = if (isPortrait) {
                val canvasWidth = with(density) { configuration.screenWidthDp.dp.toPx() }
                val canvasHeight = with(density) { configuration.screenHeightDp.dp.toPx() / 2 }
                Size(canvasWidth, canvasHeight)
            } else {
                val canvasWidth = with(density) { configuration.screenWidthDp.dp.toPx() / 2 }
                val canvasHeight = with(density) { configuration.screenHeightDp.dp.toPx() }
                Size(canvasWidth, canvasHeight)
            }

            val pegRadius = with(density) { 18.dp.toPx() }
            val gameViewModel: GameViewModel = viewModel(factory = GameViewModelFactory(canvasSize, pegRadius))

            val gameConfig = remember { gameViewModel.gameConfig }
            val pegs = remember { gameViewModel.pegs }
            val textColor = remember { gameConfig.boardConfig.boardColor }
            val backgroundColor = if (isSystemInDarkTheme()) Color(0xFF1C1C1C) else Color(0xFFE8E5DF)
            val boardModifier = Modifier
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

            if (isPortrait) {
                Column(Modifier.background(backgroundColor)) {
                    SoloTestGameRemaining(Modifier.weight(0.4f), textColor, gameViewModel.remaining, pegs.size)
                    SoloTestGame(boardModifier.weight(0.6f), gameConfig, pegs)
                }
            } else {
                Row(Modifier.background(backgroundColor)) {
                    SoloTestGameRemaining(Modifier.weight(0.4f), textColor, gameViewModel.remaining, pegs.size)
                    SoloTestGame(boardModifier.weight(0.6f), gameConfig, pegs)
                }
            }

            if (gameViewModel.isGameOver) {
                GameOverDialog(
                    textColor = textColor,
                    dialogBackgroundColor = gameConfig.boardConfig.pegColor,
                    onPlayAgainClicked = gameViewModel::onPlayAgainClicked
                )
            }
        }
    }
}
