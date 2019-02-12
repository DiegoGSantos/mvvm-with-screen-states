package com.diego.mvvmwithscreenstates.view_state

import com.diego.mvvmwithscreenstates.view.ForecastScreenState

class ForecastSuccessState(
    private val containerBackground: Int,
    private val weatherIconBackground: Int,
    private val cityNameText: String,
    private val descriptionText: String,
    private val temperatureText: String,

    container: ViewState = ViewState().withBackground(containerBackground),
    weatherIcon: ViewState = ViewState().withBackground(weatherIconBackground),
    cityName: TextViewState = TextViewState().witText(cityNameText),
    description: TextViewState = TextViewState().witText(descriptionText),
    temperature: TextViewState = TextViewState().witText(temperatureText),
    errorMessage: TextViewState = TextViewState().remove() as TextViewState,
    loading: ViewState = ViewState().remove()

) : ForecastScreenState(container, weatherIcon, cityName, description, temperature, errorMessage, loading)