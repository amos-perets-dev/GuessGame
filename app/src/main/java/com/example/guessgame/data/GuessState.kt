package com.example.guessgame.data

sealed class GuessState {

    open var title: String = ""
    open var isErrorState: Boolean = true

    object CalculateGuess : GuessState()
    object ResultError : GuessState()
    object ResultSuccess : GuessState()
}