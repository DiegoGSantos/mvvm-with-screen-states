package com.diego.mvvmwithscreenstates.view

import com.diego.mvvmwithscreenstates.view_state.TextViewState
import com.diego.mvvmwithscreenstates.view_state.ViewState

class ForecastScreenState(val llForecastContainerState: ViewState,
                          val ivForecastWeatherIconState: ViewState,
                          val tvForecastCityNameState: TextViewState,
                          val tvForecastDescriptionState: TextViewState,
                          val tvForecastTemperatureState: TextViewState,
                          val tvForecastErrorMessageState: TextViewState,
                          val pbForecastLoadingState: ViewState)