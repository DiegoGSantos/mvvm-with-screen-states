package com.diego.mvvmwithscreenstates.view_state

import android.widget.TextView

fun TextView.text(text: String): TextView {
    this.text = text
    return this
}

fun TextView.setTextViewState(textViewState: TextViewState) {
    this.setViewState(textViewState)
    this.text(textViewState.text)
}