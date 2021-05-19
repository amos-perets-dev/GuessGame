package com.example.guessgame.managers.top_player

import com.example.guessgame.data.TopPlayerRequest
import com.example.guessgame.guess_app.base.model_text.IText
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

interface ITopPlayerManager  {
    /**
     * Get the [TopPlayerRequest]
     *
     * @return [Observable]
     */
    fun getTopPlayerRequest(): Observable<TopPlayerRequest>?

    /**
     * Add the [TopPlayer] to the DB
     */
    fun addTopPlayer() : Completable?

    /**
     * Get the top players details from the DB
     *
     * @return [IText]
     */
    fun getTopPlayers(): Flowable<List<IText>>?
}