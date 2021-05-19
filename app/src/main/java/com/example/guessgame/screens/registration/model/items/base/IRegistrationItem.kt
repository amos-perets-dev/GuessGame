package com.example.guessgame.screens.registration.model.items.base

import com.example.guessgame.screens.registration.manager.RegistrationItemsManager
import io.reactivex.Observable

interface IRegistrationItem {
    fun setValidator(input: String)
    fun isValidField(): Observable<Boolean>
    fun getValue() : String
    fun getItemType(): RegistrationItemsManager.RegistrationItems
    fun getItemName(): String
}