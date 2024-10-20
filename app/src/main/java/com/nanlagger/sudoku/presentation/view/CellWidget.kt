package com.nanlagger.sudoku.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nanlagger.sudoku.presentation.state.CellState
import com.nanlagger.sudoku.presentation.state.MutableCellState
import com.nanlagger.sudoku.domain.entity.Position
import com.nanlagger.sudoku.ui.theme.ErrorBackground
import com.nanlagger.sudoku.ui.theme.SelectedBackground
import com.nanlagger.sudoku.ui.theme.TextStyles
import com.nanlagger.sudoku.ui.theme.TransparentBackground

@Composable
fun CellWidget(state: CellState, modifier: Modifier = Modifier) {
    val background = when {
        state.isError -> ErrorBackground
        state.isLight -> SelectedBackground
        else -> TransparentBackground
    }
    Box(contentAlignment = Alignment.Center, modifier = modifier.background(background)) {
        val value = if (state.value == 0) " " else state.value.toString()
        val color = if (state.isMutable) Color.Blue else Color.Black
        Text(text = value, color = color, style = TextStyles.h1)
    }
}

@Preview(showBackground = true)
@Composable
fun CellWidgetPreview() {
    CellWidget(state = MutableCellState(Position(), isMutable = false).apply { value = 2 }, modifier = Modifier
        .width(48.dp)
        .height(48.dp))
}