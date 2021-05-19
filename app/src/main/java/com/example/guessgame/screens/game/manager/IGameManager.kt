package com.example.guessgame.screens.game.manager

import com.example.guessgame.data.GuessState
import com.example.guessgame.guess_app.base.model_text.IText
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface IGameManager{
    /**
     * Add guess from the user
     *
     * @param inputGuess - current guess
     */
    fun addGuess(inputGuess: String): Completable?

    /**
     * Set the result game from the BE
     *
     * @return [GuessState]
     */
    fun setResultGame(inputGuess: String): Single<GuessState?>?

    /**
     * Get the guesses list[Int]
     *
     * @return [List][IText]
     */
    fun getGuessesListAsync(): Flowable<List<IText>>?

    /**
     * Get the guess numbers range
     */
    fun getNumbersRange() : Pair<Int, Int>

    /**
     * Call when the user click on log out from the game
     */
    fun onClickLogOut() : Completable

    /**
     * Add top player [TopPlayer]
     *
     * @param guessResult - [GuessState] current guess state(correct or incorrect)
     */
    fun addPlayer(guessResult: GuessState): Completable?

    /**
     * Get the last guess state [GuessState]
     */
    fun getLastGuessState(): GuessState?

    /**
     * Clear the [GuessState]
     */
    fun clearGuessState()

    /**
     * Disposing of the subscribers
     */
    fun dispose()
}