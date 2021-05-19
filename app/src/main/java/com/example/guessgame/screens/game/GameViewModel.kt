package com.example.guessgame.screens.game

import androidx.lifecycle.MutableLiveData
import com.example.guessgame.R
import com.example.guessgame.guess_app.base.ViewModelBase
import com.example.guessgame.screens.game.manager.IGameManager
import com.example.guessgame.data.GuessState
import com.example.guessgame.guess_app.base.model_text.IText
import com.example.guessgame.utils.SingleLiveData

class GameViewModel(
    private val gameManager: IGameManager
) :
    ViewModelBase() {

    val guessState = MutableLiveData<GuessState>()
    val guessesList = SingleLiveData<List<IText>>()
    val numbersRange = MutableLiveData<Pair<Int, Int>>()

    init {

        numbersRange.postValue(gameManager.getNumbersRange())

        val lastGuessState = gameManager.getLastGuessState()

        if (lastGuessState != null) {
            guessState.postValue(lastGuessState)
        }

        gameManager.getGuessesListAsync()
            ?.subscribe {
                guessesList.postValue(it)
            }?.let {
                compositeDisposable.add(
                    it
                )
            }

    }

    fun setInputGuess(guess: CharSequence) {

        guessState.postValue(GuessState.CalculateGuess)

        gameManager
            .addGuess(guess.toString())
            ?.andThen(gameManager.setResultGame(guess.toString()))
            ?.doOnEvent { result, error ->
                if (result != null) {
                    guessState.postValue(result)
                } else {
                    guessState.postValue(GuessState.ResultError)
                }
            }
            ?.flatMapCompletable { guessResult ->
                gameManager.addPlayer(guessResult)
            }
            ?.subscribe({}, {})
            ?.let {
                compositeDisposable.add(
                    it
                )
            }
    }

    override fun onCleared() {
        gameManager.dispose()
        super.onCleared()
    }

    /**
     * Call when the user click on the log out button
     */
    fun onClickLogOut() {
        compositeDisposable.add(
            gameManager.onClickLogOut()
                .doOnSubscribe {gameManager.clearGuessState() }
                .subscribe {
                    nextPage.postValue(R.id.action_GameFragment_to_RegistrationFragment)
                }
        )

    }

    /**
     * Call when the user click on the history button
     */
    fun onClickHistory() {
        nextPage.postValue(R.id.action_GameFragment_to_HistoryFragment)
    }

}