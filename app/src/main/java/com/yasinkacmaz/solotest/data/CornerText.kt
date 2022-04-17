package com.yasinkacmaz.solotest.data

import android.graphics.Paint
import androidx.compose.ui.geometry.Offset

data class CornerText(
    val text: String,
    val textStart: Offset,
    val rotateDegree: Float,
    val textAlign: Paint.Align
)
