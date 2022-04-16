package com.yasinkacmaz.solotest

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SoloTestGameRemaining(modifier: Modifier = Modifier, textColor: Color, remainingPegs: Int) {
    Column(
        modifier.fillMaxSize().padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
    ) {
        val textStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = textColor)
        BasicText(text = stringResource(R.string.remaining_peg, remainingPegs), style = textStyle)
        val infiniteTransition = rememberInfiniteTransition()
        val remaining = remember(remainingPegs) { Remaining.of(remainingPegs) }
        if (remaining != null) {
            val scale by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = 1.15f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1500, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
            // I need to rotate a bit because I wasn't able to rotate images with transparent pixels in Photoshop
            Image(painterResource(remaining.imageResId), null, Modifier.rotate(5f).scale(scale))
            BasicText(text = stringResource(id = remaining.textResId), style = textStyle)
            BasicText(text = stringResource(id = R.string.point, remaining.point), style = textStyle)
        } else {
            val angle by infiniteTransition.animateFloat(
                initialValue = 0F,
                targetValue = 360F,
                animationSpec = infiniteRepeatable(animation = tween(36000, easing = LinearEasing))
            )
            Image(
                painter = painterResource(id = R.drawable.points),
                contentDescription = null,
                modifier = Modifier.rotate(angle)
            )
        }
    }
}
