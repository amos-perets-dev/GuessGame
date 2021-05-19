package com.example.guessgame.screens.game.adapter

import android.view.ViewGroup
import com.example.guessgame.R
import com.example.guessgame.guess_app.base.AdapterBase
import com.example.guessgame.guess_app.base.view_holder.ViewHolderBase
import com.example.guessgame.guess_app.base.model_text.IText
import com.example.guessgame.guess_app.base.view_holder.BaseTextViewHolder

class GuessesAdapter : AdapterBase<IText>() {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBase<IText> {
        return BaseTextViewHolder(parent, false)
    }
}
