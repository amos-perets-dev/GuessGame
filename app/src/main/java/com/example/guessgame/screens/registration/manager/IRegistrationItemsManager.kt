package com.example.guessgame.screens.registration.manager

import com.example.guessgame.data.RegistrationFieldData
import com.example.guessgame.data.UserProfile
import com.example.guessgame.screens.registration.model.items.base.IRegistrationItem
import io.reactivex.Observable

interface IRegistrationItemsManager {
    fun isFormValid() : Observable<Boolean>
    fun checkField(registrationFieldData : RegistrationFieldData)
    fun getUserDetails() : UserProfile
    fun getFieldsTitles(): List<IRegistrationItem>
}