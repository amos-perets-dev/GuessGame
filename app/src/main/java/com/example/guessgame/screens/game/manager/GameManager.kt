package com.example.guessgame.screens.game.manager

import androidx.core.text.isDigitsOnly
import com.example.guessgame.managers.top_player.ITopPlayerManager
import com.example.guessgame.managers.user.IUserProfileManager
import com.example.guessgame.network.api.GuessGameApi
import com.example.guessgame.data.GuessState
import com.example.guessgame.guess_app.base.model_text.Text
import com.example.guessgame.guess_app.base.model_text.IText
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GameManager(
    private val guessTemplate: String,
    private val userProfileManager: IUserProfileManager,
    private val numbersRange: Pair<Int, Int>,
    private val network: GuessGameApi.Game,
    private val topPlayerManager: ITopPlayerManager,
    private val resultGameDefault: String,
    private val higherGuess: String,
    private val lowerGuess: String,
    private val correctGuess: String
) : IGameManager {

    private val compositeDisposable = CompositeDisposable()
    private var targetNumber: Int = -1
    private var guessState: GuessState? = null

    init {
        compositeDisposable.addAll(
            userProfileManager
                .getTargetNumberAsync()
                ?.subscribe {
                    targetNumber = it
                }
        )
    }

    override fun getGuessesListAsync(): Flowable<List<IText>>? {
        return userProfileManager
            .getGuessesListAsync()
            ?.map { dataList ->
                dataList
                    .mapIndexed { index, guess ->
                        val guessObject = createGuessObject(guess, index + 1)
                        guessObject
                    }
            }
    }

    override fun getNumbersRange() = numbersRange

    override fun onClickLogOut(): Completable {
        return userProfileManager
            .logOut()
    }

    private fun createGuessObject(guess: Int, index: Int): IText {
        val guessResult = createGuessResult(guess)
        val result = String.format(guessTemplate, index, guess, guessResult)
        return Text(result)
    }

    private fun createGuessResult(guess: Int): String {
        return when {
            guess > targetNumber -> {
                higherGuess
            }
            guess < targetNumber -> {
                lowerGuess
            }
            else -> {
                correctGuess
            }
        }
    }

    override fun addPlayer(guessResult: GuessState): Completable? {
        if (guessResult !is GuessState.ResultSuccess) return Completable.complete()
        return topPlayerManager.addTopPlayer()
    }

    override fun getLastGuessState(): GuessState? {
        return guessState
    }

    override fun clearGuessState() {
        guessState = null
    }

    override fun setResultGame(inputGuess: String): Single<GuessState?>? {
        if (inputGuess.isEmpty() ||
            inputGuess.isDigitsOnly().not()
        ) return Single.error(Throwable())
        val guess = inputGuess.toInt()

        if (guess == targetNumber) {
            return topPlayerManager
                .getTopPlayerRequest()
                ?.flatMapSingle { topPlayerRequest ->
                    network
                        .getGameMsgResult(topPlayerRequest = topPlayerRequest)
                        .subscribeOn(Schedulers.io())
                        .map {
                            guessState = GuessState.ResultSuccess
                            guessState?.title = it.message
                            guessState
                        }
                        .onErrorReturn {
                            guessState = GuessState.ResultSuccess
                            guessState?.title = resultGameDefault
                            guessState
                        }
                }?.firstOrError()

        } else {
            return Single.error(Throwable())
        }
    }

    override fun addGuess(inputGuess: String): Completable? {

        if (inputGuess.isEmpty() || inputGuess.isDigitsOnly().not()) return Completable.complete()
        val guess = inputGuess.toInt()

        return userProfileManager
            .addGuess(guess, guess == targetNumber)
    }

    override fun dispose() {
        compositeDisposable.clear()
    }

}