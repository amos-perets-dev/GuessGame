package com.example.guessgame.repo.top_player

import com.example.guessgame.data.TopPlayer
import com.example.guessgame.data.UserProfile
import com.example.guessgame.guess_app.base.model_text.IText
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.realm.RealmList
import io.realm.RealmResults

interface ITopPlayerRepo {

    /**
     * Add the [TopPlayer] to the DB
     * @param topPlayer - the current top player
     */
    fun addTopPlayer(topPlayer: TopPlayer): Completable

    /**
     * Get the top players details from the DB
     *
     * @return [RealmResults][TopPlayer]
     */
    fun getTopPlayersAsync(): Flowable<RealmResults<TopPlayer>>?

    /**
     * Get the [UserProfile] details
     *
     * @return [Observable][UserProfile]
     */
    fun getUserProfileAsync(): Observable<UserProfile>
}