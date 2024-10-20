package com.nanlagger.sudoku.presentation.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Stable
interface ValueButtonState {
    val value: Int
    val count: Int
}

class MutableValueButtonState(override val value: Int) : ValueButtonState {
    override var count: Int by mutableStateOf(9)
}