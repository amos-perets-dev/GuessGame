package com.example.guessgame.repo.user_profile

import com.example.guessgame.data.UserProfile
import com.example.guessgame.managers.realm.IRealmManager
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class UserProfileRepo(private val realmManager: IRealmManager) : IUserProfileRepo {

    override fun addUser(userProfile: UserProfile): Completable {
        return realmManager.insertAsync(userProfile)
    }

    override fun getUserProfileDataAsync(): Flowable<UserProfile?>? {
        return realmManager.getDataAsync(UserProfile::class.java)
            ?.map {
                if (it.isNullOrEmpty()) return@map UserProfile()
                return@map it.first()
            }
    }


    override fun clearUser(): Completable {
        return realmManager.clearUser()
    }

    override fun reInit(targetNumber: Int): Completable {
        return realmManager.reInit(targetNumber)
    }

    override fun addGuess(guess: Int, isFinishGame: Boolean): Completable {
        return realmManager.addGuess(guess, isFinishGame)
    }
}