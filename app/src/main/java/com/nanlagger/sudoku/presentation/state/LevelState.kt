package com.nanlagger.sudoku.presentation.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.nanlagger.sudoku.domain.entity.BoardEntity

@Stable
interface LevelState {
    val isLoading: Boolean
    val error: String?
    val boardState: BoardState
}

class MutableLevelState : LevelState {
    override var isLoading: Boolean by mutableStateOf(false)
    override var error: String? by mutableStateOf(null)
    override var boardState: MutableBoardState by mutableStateOf(MutableBoardState(BoardEntity()))
}