package com.example.guessgame.screens.registration.model.items

import com.example.guessgame.screens.registration.manager.RegistrationItemsManager
import com.example.guessgame.screens.registration.model.items.base.RegistrationItem

class NameRegistrationItemImpl(fieldTitle: String) : RegistrationItem(
    fieldTitle,
    RegistrationItemsManager.RegistrationItems.NAME
) {

    override fun validator(input: String): Boolean {
        val result = input.isEmpty().not()
        return result
    }

}