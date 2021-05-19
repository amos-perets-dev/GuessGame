package com.example.guessgame.repo.top_player

import com.example.guessgame.data.TopPlayer
import com.example.guessgame.data.UserProfile
import com.example.guessgame.managers.realm.IRealmManager
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.realm.RealmList
import io.realm.RealmResults

class TopPlayerRepo (private val realmManager : IRealmManager): ITopPlayerRepo{
    override fun addTopPlayer(topPlayer: TopPlayer): Completable {
       return realmManager.insertAsync(topPlayer)
    }

    override fun getTopPlayersAsync(): Flowable<RealmResults<TopPlayer>>? {
        return realmManager.getDataAsync(TopPlayer::class.java)
    }

    override fun getUserProfileAsync(): Observable<UserProfile> {
       return realmManager.getObject(UserProfile::class.java)
    }
}