package com.nanlagger.sudoku.domain.entity

import com.nanlagger.sudoku.domain.Const

class BoardEntity(
    private val values: Array<Array<Int>> = Array(Const.SIZE) { Array(Const.SIZE) { 0 } },
    override val difficulty: String = "None"
) : Board {

    override fun getCell(row: Int, column: Int): Int {
        return values[row][column]
    }
}