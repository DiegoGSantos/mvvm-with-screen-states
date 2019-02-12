package com.diego.mvvmwithscreenstates.view

import com.diego.mvvmwithscreenstates.view_state.TextViewState
import com.diego.mvvmwithscreenstates.view_state.ViewState

open class ForecastScreenState(
    val container: ViewState = ViewState(),
    val weatherIcon: ViewState = ViewState(),
    val cityName: TextViewState = TextViewState(),
    val description: TextViewState = TextViewState(),
    val temperature: TextViewState = TextViewState(),
    val errorMessage: TextViewState = TextViewState(),
    val loading: ViewState = ViewState()
)