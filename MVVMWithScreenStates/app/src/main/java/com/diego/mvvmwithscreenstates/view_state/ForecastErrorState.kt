package com.diego.mvvmwithscreenstates.view_state

import com.diego.mvvmwithscreenstates.R
import com.diego.mvvmwithscreenstates.view.ForecastScreenState

class ForecastErrorState(
    val text: String,
    container: ViewState = ViewState().withBackground(R.color.white),
    weatherIcon: ViewState = ViewState().remove(),
    cityName: TextViewState = TextViewState().remove() as TextViewState,
    description: TextViewState = TextViewState().remove() as TextViewState,
    temperature: TextViewState = TextViewState().remove() as TextViewState,
    errorMessage: TextViewState = TextViewState().witText(text),
    loading: ViewState = ViewState().remove()
) : ForecastScreenState(container, weatherIcon, cityName,description, temperature, errorMessage, loading)