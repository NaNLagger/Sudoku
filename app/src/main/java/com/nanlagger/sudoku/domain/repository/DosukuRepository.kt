package com.nanlagger.sudoku.domain.repository

import com.nanlagger.sudoku.domain.entity.Board

interface DosukuRepository {
    suspend fun getBoard(): Board
}