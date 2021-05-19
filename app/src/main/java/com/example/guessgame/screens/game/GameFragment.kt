package com.example.guessgame.screens.game

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.guessgame.R
import com.example.guessgame.guess_app.base.FragmentBase
import com.example.guessgame.screens.game.adapter.GuessesAdapter
import com.example.guessgame.screens.game.view_holder.GameViewHolder
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class GameFragment : FragmentBase<GameViewModel>() {
    override fun getViewModel(): Lazy<GameViewModel> = inject()

    override fun getLayoutRes(): Int = R.layout.fragment_game

    private val guessesAdapter by inject<GuessesAdapter>()
    private var gameViewHolder: GameViewHolder? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseViewModel?.numbersRange?.observe(
            viewLifecycleOwner,
            (this::createViewHolder)
        )

        baseViewModel?.nextPage?.observe(
            viewLifecycleOwner,
            (findNavController()::navigate)
        )

    }

    private fun createViewHolder(numbersRange: Pair<Int, Int>) {
        gameViewHolder = view?.let {
            GameViewHolder(
                it,
                guessesAdapter,
                numbersRange
            )
        }

        baseViewModel?.guessState?.observe(
            viewLifecycleOwner,
            { gameViewHolder?.setState(it) })

        baseViewModel?.guessesList?.observe(
            viewLifecycleOwner,
            { gameViewHolder?.setList(it) })

        compositeDisposable.addAll(
            gameViewHolder
                ?.getInputGuessText()
                ?.subscribe{baseViewModel?.setInputGuess(it)},

            gameViewHolder
                ?.getFilteredNumber()
                ?.subscribe(this::showMsg),

            gameViewHolder?.onClickLogOut()
                ?.subscribe { t1, t2 ->
                    baseViewModel?.onClickLogOut()
                },

            gameViewHolder?.onClickHistory()
                ?.doOnEvent { t1, t2 -> hideKeyboard() }
                ?.subscribe { t1, t2 ->
                    baseViewModel?.onClickHistory()
                }


        )
    }

    /**
     * Show msg
     *
     * @param filteredNumber - input from the user
     */
    private fun showMsg(filteredNumber: Int) {

        view?.let {
            Snackbar.make(
                it,
                requireContext().getString(
                    R.string.game_screen_error_guess_template_text,
                    filteredNumber.toString()
                ),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}