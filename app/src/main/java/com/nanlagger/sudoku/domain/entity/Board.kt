package com.nanlagger.sudoku.domain.entity

interface Board {
    val difficulty: String
    fun getCell(row: Int, column: Int): Int
}