package com.example.guessgame.screens.registration

import com.example.guessgame.R
import com.example.guessgame.data.RegistrationFieldData
import com.example.guessgame.screens.registration.model.RegistrationFormState
import com.example.guessgame.guess_app.base.ViewModelBase
import com.example.guessgame.managers.user.IUserProfileManager
import com.example.guessgame.repo.user_profile.IUserProfileRepo
import com.example.guessgame.screens.registration.manager.IRegistrationItemsManager
import com.example.guessgame.screens.registration.model.items.base.IRegistrationItem
import io.reactivex.Observable

class RegistrationViewModel(
    private val registrationItemsManager: IRegistrationItemsManager,
    private val userProfileManager: IUserProfileManager,
    private val userProfileRepo: IUserProfileRepo
) :
    ViewModelBase() {

    fun getFormState(): Observable<RegistrationFormState> {
        return registrationItemsManager
            .isFormValid()
            .map { isValid ->
                if (isValid){
                    RegistrationFormState.FormValid
                } else {
                    RegistrationFormState.FormUnValid
                }
            }
    }

    /**
     * Call when the user click on the submit registration
     */
    fun submitRegistration() {
        val userProfile = registrationItemsManager.getUserDetails()
        compositeDisposable.add(
            userProfileManager.addUserProfile(userProfile)
                .subscribe {
                    nextPage.postValue(R.id.action_RegistrationFragment_to_GameFragment)
                }
        )
    }

    fun setInputs(inputs: List<RegistrationFieldData>) {
        inputs.forEach (registrationItemsManager::checkField)
    }

    fun getFieldsTitles(): List<IRegistrationItem> {
        return registrationItemsManager.getFieldsTitles()
    }

}