package com.yasinkacmaz.solotest

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun GameOverDialog(textColor: Color, dialogBackgroundColor: Color, onPlayAgainClicked: () -> Unit) {
    Dialog(onDismissRequest = { }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .background(dialogBackgroundColor, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            BasicText(
                text = stringResource(id = R.string.game_over),
                style = TextStyle(fontSize = 48.sp, fontWeight = FontWeight.Bold, color = textColor)
            )
            BasicText(
                text = stringResource(id = R.string.play_again),
                style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold, color = dialogBackgroundColor),
                modifier = Modifier
                    .shadow(6.dp, RoundedCornerShape(16.dp))
                    .background(textColor, RoundedCornerShape(16.dp))
                    .clickable { onPlayAgainClicked() }
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }
    }
}