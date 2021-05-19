package com.example.guessgame.screens.registration.model.items

import android.util.Patterns
import com.example.guessgame.screens.registration.manager.RegistrationItemsManager
import com.example.guessgame.screens.registration.model.items.base.RegistrationItem

class EmailRegistrationItemImpl(fieldTitle: String) : RegistrationItem(
    fieldTitle,
    RegistrationItemsManager.RegistrationItems.EMAIL
) {

    override fun validator(input: String): Boolean {
        val result = input.isEmpty().not() && Patterns.EMAIL_ADDRESS.matcher(input).matches()
        return result
    }

}