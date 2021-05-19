package com.example.guessgame.screens.registration.view_holder

import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.guessgame.data.RegistrationFieldData
import com.example.guessgame.screens.registration.model.RegistrationFormState
import com.example.guessgame.screens.registration.manager.RegistrationItemsManager
import com.example.guessgame.screens.registration.model.items.base.IRegistrationItem
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.fragment_registration.view.*
import kotlinx.android.synthetic.main.registration_item.view.*

class RegistrationViewHolder(
    private val view: View
) {

    private var submitButton: AppCompatButton? = null

    private var registrationItemTitleName: AppCompatTextView? = null
    private var registrationItemInputName: AppCompatEditText? = null

    private var registrationItemTitleEmail: AppCompatTextView? = null
    private var registrationItemInputEmail: AppCompatEditText? = null

    init {
        submitButton = view.submit_button

        val registrationName = view.registration_name
        registrationItemTitleName = registrationName.registration_item_title
        registrationItemInputName = registrationName.registration_item_input


        val registrationEmail = view.registration_email
        registrationItemTitleEmail = registrationEmail.registration_item_title
        registrationItemInputEmail = registrationEmail.registration_item_input

    }

    fun changeButtonState(state: RegistrationFormState) {
        submitButton?.backgroundTintList =
            ContextCompat.getColorStateList(view.context, state.buttonColor)

        submitButton?.isEnabled = state.isEnabled
    }

    fun bindData(fieldsTitles: List<IRegistrationItem>?) {
        fieldsTitles?.forEach { fieldData ->
            when (fieldData.getItemType()) {
                RegistrationItemsManager.RegistrationItems.NAME -> registrationItemTitleName
                RegistrationItemsManager.RegistrationItems.EMAIL -> registrationItemTitleEmail
            }?.text = fieldData.getItemName()
        }
    }

    private fun getInputField(
        editText: AppCompatEditText?,
        registrationItems: RegistrationItemsManager.RegistrationItems
    ): Observable<RegistrationFieldData> {
        if (editText == null) return Observable.just(
            RegistrationFieldData(
                RegistrationItemsManager.RegistrationItems.NAME,
                ""
            )
        )

        return RxTextView
            .textChanges(editText)
            .map(CharSequence::toString)
            .map(String::trim)
            .map {
                RegistrationFieldData(registrationItems, it)
            }
    }

    private fun getInputName(): Observable<RegistrationFieldData> {
        return getInputField(
            registrationItemInputName,
            RegistrationItemsManager.RegistrationItems.NAME
        )
    }

    private fun getInputEmail(): Observable<RegistrationFieldData> {
        return getInputField(
            registrationItemInputEmail,
            RegistrationItemsManager.RegistrationItems.EMAIL
        )
    }

    fun getInputsFields(): Observable<List<RegistrationFieldData>> {
        return Observable.combineLatest(
            getInputEmail(),
            getInputName(),
            BiFunction { email, name ->
                return@BiFunction listOf(email, name)
            }
        )
    }

}