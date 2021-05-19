package com.example.guessgame.managers.user

import com.example.guessgame.data.UserProfile
import com.example.guessgame.repo.user_profile.IUserProfileRepo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.realm.RealmList

class UserProfileManager(
    private val userProfileRepo: IUserProfileRepo,
    private val numbersRange: Pair<Int, Int>
) : IUserProfileManager {

    private val userProfileDataAsync = userProfileRepo
        .getUserProfileDataAsync()

    override fun addUserProfile(userProfileData: UserProfile): Completable {
        userProfileData.isLoggedIn = true
        userProfileData.isFinishGame = false
        val targetNumber = calculateTargetNumber(numbersRange)
        userProfileData.targetNumber = targetNumber
        return userProfileRepo.addUser(userProfileData)
    }

    override fun reInit(): Completable {
        val targetNumber = calculateTargetNumber(numbersRange)
        return userProfileRepo.reInit(targetNumber)
    }

    /**
     * Calculate the target number that need to guess
     *
     * @return [Int] target number
     */
    private fun calculateTargetNumber(numbersRange: Pair<Int, Int>): Int {
        val minNumber = numbersRange.first
        val maxNumber = numbersRange.second
        val randomNumber = (minNumber..maxNumber).random()
        return randomNumber
    }

    override fun isLoggedIn(): Flowable<Boolean>? {
        return userProfileDataAsync
            ?.map { it.isLoggedIn }
    }

    override fun getGuessesListAsync(): Flowable<RealmList<Int>>? {
        return userProfileDataAsync
            ?.map { it.guessesList }
    }

    override fun logOut(): Completable {
       return userProfileRepo.clearUser()
    }

    override fun getTargetNumberAsync(): Flowable<Int>? {
        return userProfileDataAsync
            ?.map { it.targetNumber }
    }

    override fun addGuess(guess: Int, isFinishGame: Boolean): Completable {
        return userProfileRepo
            .addGuess(guess, isFinishGame)
    }
}