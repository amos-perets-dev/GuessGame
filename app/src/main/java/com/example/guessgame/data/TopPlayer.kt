package com.example.guessgame.data

import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import java.util.*

open class TopPlayer (
    @PrimaryKey private var id: String = "",
    var email: String = "",
    var name: String = "",
    var gameResult: Int = 0,
    ) : RealmObject(){
}