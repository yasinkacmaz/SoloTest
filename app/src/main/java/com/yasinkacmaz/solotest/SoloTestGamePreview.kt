package com.yasinkacmaz.solotest

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.yasinkacmaz.solotest.ui.theme.SoloTestTheme

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SoloTestTheme {
        SoloTestGame()
    }
}
