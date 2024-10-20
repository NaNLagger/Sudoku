package com.nanlagger.sudoku.data.repository

import com.nanlagger.sudoku.data.DosukuApi
import com.nanlagger.sudoku.domain.entity.Board
import com.nanlagger.sudoku.domain.entity.BoardEntity
import com.nanlagger.sudoku.domain.repository.DosukuRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject

class DosukuRepositoryImpl @Inject constructor(private val dosukuApi: DosukuApi): DosukuRepository {

    override suspend fun getBoard(): Board = withContext(Dispatchers.IO) {
        val response = dosukuApi.getBoard()
        val grids = response.newboard.grids
        BoardEntity(grids.first().value, grids.first().difficulty)
    }
}