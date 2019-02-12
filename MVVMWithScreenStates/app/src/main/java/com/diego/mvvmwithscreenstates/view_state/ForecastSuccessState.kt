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
    cityName: TextViewState = TextViewState().hasText(cityNameText),
    description: TextViewState = TextViewState().hasText(descriptionText),
    temperature: TextViewState = TextViewState().hasText(temperatureText),
    errorMessage: TextViewState = TextViewState().remove() as TextViewState,
    loading: ViewState = ViewState().remove()
) : ForecastScreenState(container, weatherIcon, cityName, description, temperature, errorMessage, loading)