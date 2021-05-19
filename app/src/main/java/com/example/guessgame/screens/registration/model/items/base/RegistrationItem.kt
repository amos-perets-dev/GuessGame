package com.example.guessgame.screens.registration.model.items.base

import com.example.guessgame.screens.registration.manager.RegistrationItemsManager
import com.example.guessgame.screens.registration.model.items.base.IRegistrationItem
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

abstract class RegistrationItem(
    private val fieldTitle: String,
    private val fieldType: RegistrationItemsManager.RegistrationItems) :
    IRegistrationItem {

    private val inputField = BehaviorSubject.createDefault(false)

    private var fieldValue = ""

    abstract fun validator(input: String): Boolean

    override fun getItemType(): RegistrationItemsManager.RegistrationItems = fieldType

    override fun getItemName(): String = fieldTitle

    override fun setValidator(input: String) {
        val validator = validator(input)
        inputField.onNext(validator)
        fieldValue = input
    }

    override fun isValidField(): Observable<Boolean> =
        inputField
            .hide()
            .distinctUntilChanged()

    override fun getValue(): String = fieldValue

}