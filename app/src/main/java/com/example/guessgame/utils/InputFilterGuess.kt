package com.example.guessgame.utils

import android.text.InputFilter
import android.text.Spanned
import androidx.core.text.isDigitsOnly
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class InputFilterGuess(numbersRange: Pair<Int, Int>) : InputFilter {

    private val min = numbersRange.first
    private val max = numbersRange.second

    private val filter = PublishSubject.create<Int>()

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence {

        val currInput = dest.toString().plus(source?.toString())
        return if (currInput.isDigitsOnly().not() || currInput.isEmpty()) {
            ""
        } else {
            val number = currInput.toInt()
            if (number < min || number > max) {
                filter.onNext(number)
                ""
            } else {
                source.toString()
            }
        }
    }

    fun getFilteredNumber(): Observable<Int>? {
        return filter.hide()
    }

}