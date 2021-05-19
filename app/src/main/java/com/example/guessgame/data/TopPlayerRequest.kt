package com.example.guessgame.data

import com.google.gson.annotations.SerializedName

data class TopPlayerRequest(
    @SerializedName("name")
    val name : String = "",

    @SerializedName("email")
    val email : String = "",

    @SerializedName("attempts")
    val attempts : List<Int>,

    @SerializedName("testId")
    val testId : String = "901961412243" // TEST
)
