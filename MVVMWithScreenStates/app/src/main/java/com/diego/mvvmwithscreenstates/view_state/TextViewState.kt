package com.diego.mvvmwithscreenstates.view_state

class TextViewState(textViewVisibility: ViewVisibility = ViewVisibility.VISIBLE, var text:String = ""): ViewState(textViewVisibility)

fun TextViewState.matchesTextViewState(textViewState: TextViewState): Boolean {
    matchesViewState(textViewState)
    return textViewState.text == this.text
}

fun TextViewState.hasText(text: String): TextViewState {
    this.text = text
    return this
}