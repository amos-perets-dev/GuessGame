package com.example.guessgame.data

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserProfile(
    @PrimaryKey
    private var id: Long = 0L,
    var email: String = "",
    var userName: String = "",
    var gameResult: Int = -1,
    var isFinishGame: Boolean = true,
    var isLoggedIn: Boolean = false,
    var guessesList: RealmList<Int> = RealmList<Int>(),
    var targetNumber: Int = -1,
) : RealmObject() {

}