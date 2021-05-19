package com.example.guessgame.utils.view

import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import com.example.guessgame.utils.InputFilterGuess
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable

class InputGuess(val editText: AppCompatEditText, numbersRange: Pair<Int, Int>) {
    private val filter = InputFilterGuess(numbersRange)

    init {
        editText.filters = arrayOf(filter)
    }

    fun getFilteredNumber(): Observable<Int>? {
        return filter.getFilteredNumber()
    }

    fun getInputGuess(): Observable<String> {
        return RxTextView.editorActionEvents(editText)
            .filter { actionId ->
                actionId.actionId() == EditorInfo.IME_ACTION_DONE
            }
            .map {
                it.view().text.toString()
            }
    }
}