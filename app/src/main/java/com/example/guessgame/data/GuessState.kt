package com.example.guessgame.data

sealed class GuessState {

    open var title: String = ""

    object CalculateGuess : GuessState()
    object ResultError : GuessState()
    object ResultSuccess : GuessState()
}