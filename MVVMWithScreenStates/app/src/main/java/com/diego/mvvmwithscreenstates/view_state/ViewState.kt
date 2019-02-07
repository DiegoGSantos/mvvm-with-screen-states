package com.diego.mvvmwithscreenstates.view_state

open class ViewState(var visibility: ViewVisibility = ViewVisibility.VISIBLE, var background: Int = 0)

fun ViewState.matchesViewState(expectedViewState: ViewState): Boolean {
    return  visibility == expectedViewState.visibility && background == expectedViewState.background
}

fun ViewState.show(): ViewState {
    this.visibility = ViewVisibility.VISIBLE
    return this
}

fun ViewState.hide(): ViewState {
    this.visibility = ViewVisibility.INVISIBLE
    return this
}

fun ViewState.remove(): ViewState {
    this.visibility = ViewVisibility.GONE
    return this
}

fun ViewState.withBackground(backgroundColor: Int): ViewState {
    this.background = backgroundColor
    return this
}