package com.nanlagger.sudoku.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.nanlagger.sudoku.presentation.state.BoardState
import com.nanlagger.sudoku.domain.Const
import com.nanlagger.sudoku.presentation.state.MutableBoardState
import com.nanlagger.sudoku.domain.entity.BoardEntity
import com.nanlagger.sudoku.ui.theme.SudokuTheme

@Composable
fun BoardWidget(
    modifier: Modifier = Modifier,
    boardState: BoardState,
    onCellClick: (Int, Int) -> Unit
) {
    BoardLayout(
        content = {
            for (i in 0 until Const.SIZE) {
                for (j in 0 until Const.SIZE) {
                    CellWidget(state = boardState.cells[i][j], Modifier.clickable { onCellClick(i, j) })
                }
            }
        },
        modifier = modifier
    )
}
//
//@Composable
//fun BoardHolder(content: @Composable () -> Unit, modifier: Modifier) {
//    Layout(content = content, modifier = modifier.as) { measurables, constraints ->
//        val size = constraints.maxWidth
//        layout(size, size) {
//
//        }
//    }
//}

@Composable
fun BoardLayout(content: @Composable () -> Unit, modifier: Modifier) {
    Layout(
        content = content,
        modifier = modifier
            .background(Color.White)
            .drawBehind {
                val gap = size.width / 9
                var offset = 0f
                var weight = 1f
                for (j in 0 until 10) {
                    weight = if (j % 3 == 0) 4f else 1f
                    drawLine(
                        color = Color.Black,
                        start = Offset(offset, 0f),
                        end = Offset(offset, size.height),
                        strokeWidth = weight
                    )
                    drawLine(
                        color = Color.Black,
                        start = Offset(0f, offset),
                        end = Offset(size.width, offset),
                        strokeWidth = weight
                    )
                    offset += gap
                }
            }
    ) { measurables, constraints ->
        val cellSize = minOf(constraints.maxWidth, constraints.maxHeight) / 9
        val size = cellSize * 9
        var i = 0
        var j = 0
        val placeables = measurables.map { measurable ->
            measurable.measure(Constraints(minHeight = cellSize, minWidth = cellSize))
        }
        layout(size, size) {
            placeables.forEach { placeable ->
                val x = cellSize * j
                val y = cellSize * i
                placeable.placeRelative(x, y)
                j++
                if (j >= 9) {
                    i++
                    j = 0
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = android.graphics.Color.GRAY.toLong(), widthDp = 480, heightDp = 480)
@Composable
fun DeskPreview() {
    SudokuTheme {
        BoardWidget(boardState = MutableBoardState(BoardEntity()), onCellClick = { _, _ -> })
    }
}