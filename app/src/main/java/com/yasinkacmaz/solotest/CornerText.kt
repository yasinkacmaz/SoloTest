package com.yasinkacmaz.solotest

import android.graphics.Paint
import androidx.compose.ui.geometry.Offset

data class CornerText(
    val corner: Corner,
    val text: String,
    val textStart: Offset,
    val rotateDegree: Float,
    val pivot: Offset,
    val textAlign: Paint.Align
)
