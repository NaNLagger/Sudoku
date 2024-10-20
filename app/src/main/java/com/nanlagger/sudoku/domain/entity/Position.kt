package com.nanlagger.sudoku.domain.entity

data class Position(
    val row: Int = 0,
    val col: Int = 0
) {
    val box = 3 * (row / 3) + (col / 3)
}