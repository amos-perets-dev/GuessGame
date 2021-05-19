package com.example.guessgame.guess_app.base.view_holder

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.example.guessgame.R
import com.example.guessgame.guess_app.base.model_text.IText
import kotlinx.android.synthetic.main.text_view_item.view.*

open class BaseTextViewHolder(
    parent: ViewGroup,
    private val isShowBackground : Boolean = true,
    @LayoutRes layout: Int = R.layout.text_view_item
) :
    ViewHolderBase<IText>(parent, layout) {

    override fun bindData(model: IText) {
        val guessItemText = itemView.item_text
        guessItemText.text = model.getValue()
        if (isShowBackground.not()){
            guessItemText.background = null
        }
    }

}