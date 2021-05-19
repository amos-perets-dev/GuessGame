package com.example.guessgame.screens.registration.model

import androidx.annotation.ColorRes
import com.example.guessgame.R

sealed class RegistrationFormState {

    @ColorRes
    open val buttonColor: Int = R.color.registration_screen_submit_button_disable_color
    open val isEnabled: Boolean = false

    object FormValid : RegistrationFormState() {
        override val buttonColor: Int get() = R.color.registration_screen_submit_button_enable_color
        override val isEnabled: Boolean get() = true
    }

    object FormUnValid : RegistrationFormState() {
        override val buttonColor: Int get() = R.color.registration_screen_submit_button_disable_color
        override val isEnabled: Boolean get() = false
    }
}