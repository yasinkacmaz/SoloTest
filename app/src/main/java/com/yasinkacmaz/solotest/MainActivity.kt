package com.yasinkacmaz.solotest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.toSize
import com.yasinkacmaz.solotest.ui.theme.SoloTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SoloTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    val canvasSize = remember { mutableStateOf(Size.Zero) }
                    val pegRadius = 12.dpToPx()

                    val boardConfig = derivedStateOf {
                        val boardRadius = canvasSize.value.minDimension / 2.4f
                        val boardCenter = Offset(x = canvasSize.value.width / 2, y = canvasSize.value.height / 2)
                        BoardConfig(
                            boardRadius = boardRadius,
                            boardCenter = boardCenter,
                            pegSize = pegRadius * 2
                        )
                    }.value
                    val playableBoardIndexes = boardConfig.boardIndexes.also { it.removeAll(boardConfig.cornerIndexes) }
                    val pegs = PegPlacer.placeToBoard(boardConfig)
                    val modifier = Modifier
                        .fillMaxSize()
                        .onGloballyPositioned { canvasSize.value = it.size.toSize() }

                    SoloTestGame(modifier, boardConfig, playableBoardIndexes, pegs)
                }
            }
        }
    }
}
