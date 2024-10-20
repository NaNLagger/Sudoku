package com.nanlagger.sudoku.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

object TextStyles {

    val defaultTextStyle = TextStyle(
        fontSize = 16.sp,
    )

    val h1 = TextStyle(
        fontSize = 26.sp
    )

    val h1Blue = h1.copy(color = Blue500)

    val caption = TextStyle(
        fontSize = 12.sp
    )

}