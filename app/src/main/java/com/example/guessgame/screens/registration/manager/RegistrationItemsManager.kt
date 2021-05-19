package com.example.guessgame.screens.registration.manager

import com.example.guessgame.data.RegistrationFieldData
import com.example.guessgame.data.UserProfile
import com.example.guessgame.screens.registration.model.items.base.IRegistrationItem
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class RegistrationItemsManager(
    private val nameItem: IRegistrationItem,
    private val emailItem: IRegistrationItem
) : IRegistrationItemsManager {

    enum class RegistrationItems {
        NAME,
        EMAIL
    }

    override fun isFormValid(): Observable<Boolean> =
        Observable.combineLatest(
            nameItem.isValidField(),
            emailItem.isValidField(),
            BiFunction { isNameValid, isEmailValid ->
                return@BiFunction isNameValid &&
                        isEmailValid
            }
        ).distinctUntilChanged()
            .startWith(false)

    override fun checkField(registrationFieldData: RegistrationFieldData) {
        val value = registrationFieldData.value
        when (registrationFieldData.fieldType) {
            RegistrationItems.NAME -> nameItem.setValidator(value)
            RegistrationItems.EMAIL -> emailItem.setValidator(value)
        }
    }

    override fun getFieldsTitles(): List<IRegistrationItem> {
        return listOf(nameItem, emailItem)
    }

    override fun getUserDetails(): UserProfile {
        return UserProfile(email = emailItem.getValue(), userName = nameItem.getValue())
    }

}