package com.example.guessgame.guess_app.base.model_text

class Text(private val text: String) : IText {
    override fun getValue(): String = text
}