package com.example.guessgame.screens.registration

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.guessgame.R
import com.example.guessgame.guess_app.base.FragmentBase
import com.example.guessgame.screens.registration.view_holder.RegistrationViewHolder
import kotlinx.android.synthetic.main.fragment_registration.view.*
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RegistrationFragment : FragmentBase<RegistrationViewModel>() {
    override fun getViewModel(): Lazy<RegistrationViewModel> = inject()

    override fun getLayoutRes(): Int = R.layout.fragment_registration

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewHolder = RegistrationViewHolder(view)

        val submitButton = view.submit_button

        compositeDisposable.addAll(
            viewHolder
                .getInputsFields()
                .doOnNext { baseViewModel?.setInputs(it) }
                .subscribe(),

            baseViewModel?.getFormState()
                ?.doOnNext(viewHolder::changeButtonState)
                ?.subscribe()
        )

        viewHolder.bindData(baseViewModel?.getFieldsTitles())

        submitButton.setOnClickListener {
            baseViewModel?.submitRegistration()
        }

        baseViewModel?.nextPage?.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(it)
        })
    }
}