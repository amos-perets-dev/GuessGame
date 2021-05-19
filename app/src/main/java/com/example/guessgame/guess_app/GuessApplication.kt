package com.example.guessgame.guess_app

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GuessApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Android context
            androidContext(applicationContext)
            // modules
            modules(GuessModules().createModules(applicationContext))
        }
    }

}