package com.yasinkacmaz.solotest

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

@Suppress("unused")
enum class Remaining(@StringRes val textResId: Int, val point: Int, @DrawableRes val imageResId: Int) {
    REMAINING_1(R.string.remaining_1, 200, R.drawable.remaining_1),
    REMAINING_2(R.string.remaining_2, 175, R.drawable.remaining_2),
    REMAINING_3(R.string.remaining_3, 150, R.drawable.remaining_3),
    REMAINING_4(R.string.remaining_4, 125, R.drawable.remaining_4),
    REMAINING_5(R.string.remaining_5, 100, R.drawable.remaining_5),
    REMAINING_6(R.string.remaining_6, 75, R.drawable.remaining_6),
    REMAINING_7(R.string.remaining_7, 50, R.drawable.remaining_7),
    REMAINING_8(R.string.remaining_8, 25, R.drawable.remaining_8),
    REMAINING_9(R.string.remaining_9, 0, R.drawable.remaining_9);

    companion object {
        private val values = values()

        fun of(remainingPegs: Int): Remaining? {
            return if (remainingPegs > values.size) null else values[remainingPegs - 1]
        }
    }
}
