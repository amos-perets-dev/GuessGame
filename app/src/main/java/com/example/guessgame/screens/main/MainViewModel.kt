package com.example.guessgame.screens.main

import com.example.guessgame.guess_app.base.ViewModelBase
import com.example.guessgame.managers.user.IUserProfileManager
import com.example.guessgame.screens.game.manager.IGameManager
import io.reactivex.Single

class MainViewModel(
    private val userProfileManager: IUserProfileManager,
    private val gameManager: IGameManager
) : ViewModelBase() {

    init {
        userProfileManager.reInit()
            ?.subscribe()?.let {
                compositeDisposable.add(
                    it
                )
            }
    }

    fun isLoggedIn(): Single<Boolean>? {
        return userProfileManager
            .isLoggedIn()
            ?.firstOrError()
    }

    fun dispose(){
        gameManager.clearGuessState()
    }

}