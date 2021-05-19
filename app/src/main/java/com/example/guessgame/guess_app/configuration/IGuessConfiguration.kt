package com.example.guessgame.guess_app.configuration

interface IGuessConfiguration {
    fun getNumbersRange(): Pair<Int, Int>
}