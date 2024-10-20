package com.nanlagger.sudoku.presentation.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.nanlagger.sudoku.domain.entity.Position

@Stable
interface CellState {
    val value: Int
    val isMutable: Boolean
    val position: Position
    val isError: Boolean
    val isLight: Boolean
}

class MutableCellState(override val position: Position, override val isMutable: Boolean = true) : CellState {
    override var value: Int by mutableIntStateOf(0)
    override var isError: Boolean by mutableStateOf(false)
    override var isLight: Boolean by mutableStateOf(false)
}
