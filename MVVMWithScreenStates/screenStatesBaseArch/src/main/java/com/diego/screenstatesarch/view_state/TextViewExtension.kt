package com.diego.screenstatesarch.view_state

import android.widget.TextView
import com.diego.mvvmwithscreenstates.view_state.TextViewState
import com.diego.mvvmwithscreenstates.view_state.setViewState

fun TextView.text(text: String): TextView {
    this.text = text
    return this
}

fun TextView.setTextViewState(textViewState: TextViewState) {
    this.setViewState(textViewState)
    this.text(textViewState.text)
}