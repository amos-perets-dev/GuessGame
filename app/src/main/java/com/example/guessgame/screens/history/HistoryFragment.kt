package com.example.guessgame.screens.history

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.guessgame.R
import com.example.guessgame.guess_app.base.FragmentBase
import com.example.guessgame.screens.history.adapter.HistoryAdapter
import com.example.guessgame.screens.history.view_holder.HistoryScreenViewHolder
import com.example.guessgame.screens.main.MainActivity
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HistoryFragment : FragmentBase<HistoryViewModel>() {
    override fun getViewModel(): Lazy<HistoryViewModel> = inject()

    override fun getLayoutRes(): Int = R.layout.fragment_history

    private val historyAdapter by inject<HistoryAdapter>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    baseViewModel?.onClickBack()
                }
            })

        val viewHolder = HistoryScreenViewHolder(view, historyAdapter)

        viewHolder
            .onClickBack()
            ?.subscribe { t1, t2 ->
                baseViewModel?.onClickBack()
            }?.let {
                compositeDisposable.add(
                    it
                )
            }

        baseViewModel
            ?.getTopPlayers()
            ?.subscribe { data, error ->
                viewHolder.bindData(data)
            }?.let {
                compositeDisposable.add(
                    it
                )
            }

        baseViewModel?.nextPage?.observe(viewLifecycleOwner, Observer(findNavController()::navigate))

    }
}