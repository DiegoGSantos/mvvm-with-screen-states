package com.diego.mvvmwithscreenstates.view_state

import android.view.View
import android.view.View.*

fun View.visible(): View {
    this.visibility = VISIBLE
    return this
}

fun View.invisible(): View {
    this.visibility = INVISIBLE
    return this
}

fun View.notPresent(): View{
    this.visibility = GONE
    return this
}

fun View.visibility(visibility: ViewVisibility) {
    when(visibility) {
        ViewVisibility.VISIBLE -> this.visible()
        ViewVisibility.INVISIBLE -> this.invisible()
        ViewVisibility.GONE -> this.notPresent()
    }
}

fun View.setViewState(viewState: ViewState) {
    this.visibility(viewState.visibility)
    if (viewState.background > 0) this.setBackgroundResource(viewState.background)
}