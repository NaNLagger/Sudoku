package com.nanlagger.sudoku.data

import retrofit2.http.GET

interface DosukuApi {

    @GET("dosuku?query={newboard(limit:1){grids{value,difficulty}}}")
    suspend fun getBoard(): DosukuResponse
}