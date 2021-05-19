package com.example.guessgame.repo.user_profile

import com.example.guessgame.data.UserProfile
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable


interface IUserProfileRepo  {

    /**
     * Add [UserProfile] to the DB
     *
     * @param userProfile - current [UserProfile]
     */
    fun addUser(userProfile: UserProfile): Completable

    /**
     * Get [UserProfile] details from the DB
     */
    fun getUserProfileDataAsync(): Flowable<UserProfile?>?

    /**
     * Add guess to the DB
     *
     * @param guess - current guess
     * @param isFinishGame - if it is a correct guess
     */
    fun addGuess(guess: Int, isFinishGame: Boolean): Completable

    /**
     * Clear the [UserProfile] DB
     */
    fun clearUser(): Completable

    /**
     * Re-Init the [UserProfile] details
     *
     * @param targetNumber - the new target number
     */
    fun reInit(targetNumber: Int): Completable
}