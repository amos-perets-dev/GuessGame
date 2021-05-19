package com.example.guessgame.screens.game.view_holder

import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.guessgame.data.GuessState
import com.example.guessgame.guess_app.base.model_text.IText
import com.example.guessgame.screens.game.adapter.GuessesAdapter
import com.example.guessgame.utils.view.InputGuess
import com.jakewharton.rxbinding2.view.RxView
import com.wang.avi.AVLoadingIndicatorView
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_game.view.*

class GameViewHolder(
    view: View,
    private val guessesAdapter: GuessesAdapter,
    numbersRange: Pair<Int, Int>
) {
    private var inputGuess = InputGuess(view.game_guess_input, numbersRange)

    private val recyclerViewGuesses: RecyclerView = view.recycler_view_guesses
    private val gameGuessInput: AppCompatEditText = view.game_guess_input
    private val loaderCalculateGuess: AVLoadingIndicatorView = view.loader_calculate_guess
    private val gameGuessResult: AppCompatTextView = view.game_guess_result
    private val gameLogoutTitle: AppCompatTextView = view.game_logout_title
    private val gameHistoryTitle: AppCompatTextView = view.back_button_title

    init {
        recyclerViewGuesses.adapter = guessesAdapter
    }

    /**
     * Call when the user click on the log out button
     */
    fun onClickLogOut(): Single<Any>? {
        return RxView.clicks(gameLogoutTitle)
            .firstOrError()
    }

    /**
     * Call when the user click on the history button
     */
    fun onClickHistory(): Single<Any>? {
        return RxView.clicks(gameHistoryTitle)
            .firstOrError()
    }

    /**
     * Get the input text from the [gameGuessInput]
     */
    fun getInputGuessText(): Observable<String> {
        return inputGuess.getInputGuess()
    }

    /**
     * Set the current state according to the user guess
     */
    fun setState(guessState: GuessState?) {
        when (guessState) {
            GuessState.CalculateGuess -> {
                gameGuessInput.visibility = View.GONE
                loaderCalculateGuess.visibility = View.VISIBLE
                gameLogoutTitle.visibility = View.INVISIBLE
                gameHistoryTitle.visibility = View.INVISIBLE
            }
            GuessState.ResultError, GuessState.ResultSuccess -> {
                gameLogoutTitle.visibility = View.VISIBLE
                gameHistoryTitle.visibility = View.VISIBLE
                loaderCalculateGuess.visibility = View.INVISIBLE

                if (guessState is GuessState.ResultError){
                    gameGuessInput.text?.clear()
                    gameGuessInput.visibility = View.VISIBLE
                } else {
                    gameGuessResult.text = guessState.title
                    gameGuessResult.visibility = View.VISIBLE
                    gameGuessInput.visibility = View.GONE
                }


            }
        }
    }

    /**
     * Set the guesses list
     *
     * @param data - [List][IText]
     */
    fun setList(data: List<IText>?) {
        guessesAdapter.submitList(data)
    }

    /**
     * Get the filtered number
     *
     * @return [Observable][Int] - filtered number
     */
    fun getFilteredNumber(): Observable<Int>? {
        return inputGuess.getFilteredNumber()
    }
}