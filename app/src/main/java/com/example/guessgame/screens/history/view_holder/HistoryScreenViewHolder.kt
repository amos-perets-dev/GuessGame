package com.example.guessgame.screens.history.view_holder

import android.view.View
import com.example.guessgame.guess_app.base.model_text.IText
import com.example.guessgame.screens.history.adapter.HistoryAdapter
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_history.view.*

class HistoryScreenViewHolder(
    view: View,
    private val historyAdapter: HistoryAdapter
) {

    private val backButton = view.back_button_title

    init {
        view.recycler_view_top_players.adapter = historyAdapter
    }

    fun bindData(list : List<IText>) {
        historyAdapter.submitList(list)
    }

    fun onClickBack(): Single<Any>? {
        return RxView.clicks(backButton)
            .firstOrError()
    }


}