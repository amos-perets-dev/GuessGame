package com.example.guessgame.guess_app.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable


abstract class FragmentBase<VM : ViewModelBase> : Fragment() {

    protected val compositeDisposable = CompositeDisposable()
    protected var baseViewModel: VM? = null

    abstract fun getViewModel(): Lazy<VM>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        baseViewModel = getViewModel().value
        // Inflate the layout for this fragment
        return inflater.inflate(getLayoutRes(), container, false)
    }

    open fun hideKeyboard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }
}