package com.nanlagger.sudoku.presentation.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nanlagger.sudoku.presentation.state.ValueButtonState
import com.nanlagger.sudoku.presentation.viewmodel.LevelViewModel
import com.nanlagger.sudoku.presentation.viewmodel.LevelViewModelImpl
import com.nanlagger.sudoku.presentation.viewmodel.PreviewLevelViewModel
import com.nanlagger.sudoku.ui.theme.TextStyles

@Composable
fun LevelScreen(modifier: Modifier = Modifier, viewModel: LevelViewModel = viewModel<LevelViewModelImpl>()) {
    Column(modifier) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        ) {
            Button(onClick = viewModel::onNewClicked) {
                Text(text = "New Board")
            }
            Text(
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(start = 16.dp),
                text = "Difficulty: ${viewModel.state.boardState.difficulty}"
            )
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .aspectRatio(1f)) {
            this@Column.AnimatedVisibility(
                visible = viewModel.state.isLoading.not(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                BoardWidget(
                    boardState = viewModel.state.boardState,
                    onCellClick = { row, column -> viewModel.onCellClick(row, column) }
                )
            }
        }
        Actions(Modifier.align(Alignment.CenterHorizontally)) { viewModel.onEraseClick() }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly
        ) {
            for (valueButtonState in viewModel.state.boardState.valueButtons) {
                ValueButton(viewModel::onValueClick, valueButtonState, Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun Actions(modifier: Modifier, onClick: () -> Unit) {
    Row(modifier) {
        Button(onClick = onClick) {
            Text(text = "Erase")
        }
    }
}

@Composable
private fun ValueButton(onClick: (Int) -> Unit, state: ValueButtonState, modifier: Modifier) {
    var cardModifier = modifier
        .padding(2.dp)
        .clickable(
            enabled = state.count > 0,
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(),
            onClick = { onClick(state.value) })
    if (state.count > 0) {
        cardModifier = cardModifier.alpha(1f)
    } else {
        cardModifier = cardModifier.alpha(0.5f)
    }
    ElevatedCard(
        modifier = cardModifier,
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = TextStyles.h1Blue,
            text = state.value.toString()
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 2.dp),
            style = TextStyles.caption,
            text = state.count.toString()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LevelScreenPreview() {
    LevelScreen(viewModel = PreviewLevelViewModel())
}