package com.nanlagger.sudoku.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class DosukuResponse(
    @SerialName("newboard")
    val newboard: NewBoardResponse
)

@Serializable
class NewBoardResponse(
    @SerialName("grids")
    val grids: List<BoardResponse>
)

@Serializable
class BoardResponse(
    @SerialName("value")
    val value: Array<Array<Int>>,
    @SerialName("difficulty")
    val difficulty: String
)