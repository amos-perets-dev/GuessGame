package com.example.guessgame.screens.history

import com.example.guessgame.R
import com.example.guessgame.guess_app.base.ViewModelBase
import com.example.guessgame.guess_app.base.model_text.IText
import com.example.guessgame.managers.top_player.ITopPlayerManager
import io.reactivex.Single

class HistoryViewModel(
    private val topPlayerManager: ITopPlayerManager
) :
    ViewModelBase() {

    /**
     * Get the top players list
     *
     * @return [Single][List][IText]
     */
    fun getTopPlayers(): Single<List<IText>>? {
        return topPlayerManager.getTopPlayers()
            ?.firstOrError()
    }

    /**
     * Call when the user click on the back button
     */
    fun onClickBack() {
        nextPage.postValue(R.id.action_HistoryFragment_to_GameFragment)
    }
}