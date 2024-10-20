package com.nanlagger.sudoku.presentation.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.nanlagger.sudoku.domain.Const
import com.nanlagger.sudoku.domain.entity.Board
import com.nanlagger.sudoku.domain.entity.Position

@Stable
interface BoardState {
    val difficulty: String
    val cells: List<List<CellState>>
    val valueButtons: List<ValueButtonState>

    fun setSelected(position: Position)
    fun setValue(value: Int)
}

class MutableBoardState(board: Board) : BoardState {
    private val mapValues = mutableMapOf<Int, MutableList<MutableCellState>>()
    private val mutableCells = List(Const.SIZE) { row ->
        List(Const.SIZE) { column ->
            val cell = board.getCell(row, column)
            val mutableCell = MutableCellState(Position(row, column), isMutable = cell == 0)
            mutableCell.value = cell
            mapValues.getOrPut(cell) { mutableListOf() }.add(mutableCell)
            mutableCell
        }
    }
    private var selectedPosition: Position by mutableStateOf(Position(0, 0))
    override val difficulty: String = board.difficulty
    override val cells: List<List<CellState>> = mutableCells
    override val valueButtons: List<MutableValueButtonState> = List(Const.SIZE) { index ->
        val value = index + 1
        val count = Const.SIZE - (mapValues[value]?.size ?: 0)
        MutableValueButtonState(value).also { it.count = count }
    }

    override fun setSelected(position: Position) {
        if (selectedPosition == position) return
        val oldSelected = mutableCells[selectedPosition.row][selectedPosition.col]
        setLight(oldSelected, false)
        selectedPosition = position
        val cellState = mutableCells[position.row][position.col]
        setLight(cellState, true)
    }

    override fun setValue(value: Int) {
        val cell = mutableCells[selectedPosition.row][selectedPosition.col]
        if (cell.isMutable) {
            changeValueButton(cell.value, value)
            hideError(cell)
            setLight(cell, false)
            val sameValueCells = mapValues[cell.value]
            sameValueCells?.remove(cell)
            cell.value = value
            mapValues[cell.value]?.add(cell)
            setLight(cell, true)
            if (value != 0) {
                checkSameValue(cell)?.let {
                    cell.isError = true
                    it.isError = true
                }
            }
        }
    }

    private fun setLight(cell: MutableCellState, isLight: Boolean = false) {
        cell.isLight = isLight
        if (cell.value != 0) {
            mapValues[cell.value]?.forEach { it.isLight = isLight }
        }
    }

    private fun checkSameValue(cell: MutableCellState): MutableCellState? {
        val sameValues = mapValues[cell.value] ?: return null
        val same = sameValues.find {
            it != cell &&
                    (it.position.row == cell.position.row
                            || it.position.col == cell.position.col
                            || it.position.box == cell.position.box)
        }
        return same
    }

    private fun hideError(cell: MutableCellState) {
        if (cell.isError) {
            mapValues[cell.value]?.forEach { it.isError = false }
        }
    }

    private fun changeValueButton(from: Int, to: Int) {
        if (from > 0) {
            valueButtons[from - 1].count++
        }
        if (to > 0) {
            valueButtons[to - 1].count--
        }
    }
}