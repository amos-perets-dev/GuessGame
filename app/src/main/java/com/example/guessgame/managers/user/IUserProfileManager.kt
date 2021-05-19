package com.example.guessgame.managers.user

import com.example.guessgame.data.UserProfile
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.realm.RealmList

interface IUserProfileManager  {
    /**
     * Add [UserProfile] to the DB
     *
     * @param userProfileData - current [UserProfile]
     */
    fun addUserProfile(userProfileData: UserProfile) : Completable

    /**
     * Get the target number(need to guess)
     *
     * @return [Int][]
     */
    fun getTargetNumberAsync(): Flowable<Int>?

    /**
     * Add guess to the DB
     *
     * @param guess - current guess
     * @param isFinishGame - if it is a correct guess
     */
    fun addGuess(guess: Int, isFinishGame: Boolean): Completable

    /**
     * Get the logged in state
     */
    fun isLoggedIn(): Flowable<Boolean>?

    /**
     * Get the guesses list[Int] from the DB
     *
     * @return [RealmList][Int]
     */
    fun getGuessesListAsync(): Flowable<RealmList<Int>>?

    /**
     * Clear the [UserProfile] DB
     */
    fun logOut(): Completable

    /**
     * Re-Init the [UserProfile] details
     */
    fun reInit(): Completable?
}