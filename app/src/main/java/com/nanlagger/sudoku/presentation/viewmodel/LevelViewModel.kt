package com.nanlagger.sudoku.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nanlagger.sudoku.di.ComponentManager
import com.nanlagger.sudoku.domain.entity.Position
import com.nanlagger.sudoku.presentation.state.LevelState
import com.nanlagger.sudoku.presentation.state.MutableBoardState
import com.nanlagger.sudoku.presentation.state.MutableLevelState
import kotlinx.coroutines.launch

class LevelViewModelImpl : LevelViewModel() {

    private val repository = ComponentManager.getAppComponent().dosukuRepository

    private var mutableState: MutableLevelState = MutableLevelState()
    override val state: LevelState
        get() = mutableState

    init {
        loadBoard()
    }

    override fun onCellClick(row: Int, column: Int) {
        state.boardState.setSelected(Position(row, column))
    }

    override fun onValueClick(value: Int) {
        state.boardState.setValue(value)
    }

    override fun onEraseClick() {
        state.boardState.setValue(0)
    }

    override fun onNewClicked() {
        loadBoard()
    }

    private fun loadBoard() {
        viewModelScope.launch {
            mutableState.isLoading = true
            val result = kotlin.runCatching { repository.getBoard() }
            if (result.isSuccess) {
                mutableState.boardState = MutableBoardState(result.getOrThrow())
            } else {
                mutableState.error = result.exceptionOrNull()?.message
            }
            mutableState.isLoading = false
        }
    }
}

abstract class LevelViewModel : ViewModel() {
    abstract val state: LevelState

    abstract fun onCellClick(row: Int, column: Int)
    abstract fun onValueClick(value: Int)
    abstract fun onEraseClick()
    abstract fun onNewClicked()
}

class PreviewLevelViewModel : LevelViewModel() {
    override val state: LevelState = MutableLevelState()

    override fun onCellClick(row: Int, column: Int) {}
    override fun onValueClick(value: Int) {}
    override fun onEraseClick() {}
    override fun onNewClicked() {}
}