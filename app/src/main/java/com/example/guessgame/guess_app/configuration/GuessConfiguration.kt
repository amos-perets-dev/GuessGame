package com.example.guessgame.guess_app.configuration

class GuessConfiguration : IGuessConfiguration {

    companion object {
        private const val MIN_NUMBER = 1
        private const val MAX_NUMBER = 10
    }

    override fun getNumbersRange(): Pair<Int, Int> {
        return Pair(MIN_NUMBER, MAX_NUMBER)
    }

    fun getTargetNumber(){

    }

}