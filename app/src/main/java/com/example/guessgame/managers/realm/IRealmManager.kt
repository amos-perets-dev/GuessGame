package com.example.guessgame.managers.realm

import com.example.guessgame.data.UserProfile
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.realm.RealmObject
import io.realm.RealmResults


interface IRealmManager {

    /**
     * Insert the object to the [Realm]
     */
    fun <E : RealmObject> insertAsync(`object`: E): Completable

    /**
     * Get the data list from the [Realm]
     */
    fun <E : RealmObject> getDataAsync(type: Class<E>): Flowable<RealmResults<E>>?

    /**
     * Get the object from the [Realm]
     */
    fun <E : RealmObject> getObject(type: Class<E>): Observable<E>

    /**
     * Set the logged in state
     */
    fun setIsLoggedIn(isLoggedIn: Boolean): Completable

    /**
     * Add guess to the [Realm]
     */
    fun addGuess(guess: Int, isFinishGame: Boolean): Completable

    /**
     * Delete the [UserProfile] from the [Realm]
     */
    fun clearUser(): Completable

    /**
     * Re-Init the [UserProfile] details
     */
    fun reInit(targetNumber: Int): Completable
}