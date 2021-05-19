package com.example.guessgame.data

import com.google.gson.annotations.SerializedName

data class GameResultResponse(
    @SerializedName("message")
    val message : String = ""
)
