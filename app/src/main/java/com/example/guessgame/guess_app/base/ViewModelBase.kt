package com.example.guessgame.guess_app.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.guessgame.utils.SingleLiveData
import io.reactivex.disposables.CompositeDisposable

open class ViewModelBase :
    ViewModel() {

    protected val compositeDisposable = CompositeDisposable()
     val nextPage = SingleLiveData<Int>()

    val showError = MutableLiveData<Int>()

    fun setError(error: Throwable) {

    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}