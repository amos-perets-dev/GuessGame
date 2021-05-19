package com.example.guessgame.data

import com.example.guessgame.screens.registration.manager.RegistrationItemsManager

data class RegistrationFieldData(
    val fieldType: RegistrationItemsManager.RegistrationItems,
    val value: String = ""
)