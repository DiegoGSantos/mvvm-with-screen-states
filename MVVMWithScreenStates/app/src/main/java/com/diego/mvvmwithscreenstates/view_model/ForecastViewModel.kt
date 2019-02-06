package com.diego.mvvmwithscreenstates.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diego.mvvmwithscreenstates.R
import com.diego.mvvmwithscreenstates.model.Forecast
import com.diego.mvvmwithscreenstates.rest_client.*
import com.diego.mvvmwithscreenstates.view.ForecastScreenState
import com.diego.mvvmwithscreenstates.view_state.TextViewState
import com.diego.mvvmwithscreenstates.view_state.ViewState
import com.diego.mvvmwithscreenstates.view_state.ViewVisibility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class ForecastViewModel: ViewModel() {
    val forecastLiveData: MutableLiveData<ForecastScreenState> = MutableLiveData()

    suspend fun getForecast(city: String) {

//        Service.create().getForecast(city).awaitResponse(::onRequestSuccess, ::onError)

        onLoading()
        val result = Service.create().getForecast(city).awaitResult()

        when (result) {
            is Result.Success -> onRequestSuccess(result.data)
            is Result.Error -> onError(result.throwable)
        }
    }

    private fun onLoading() {
        forecastLiveData.value = ForecastScreenState(
            llForecastContainerState = ViewState(background = getBackgroundColor("")),
            ivForecastWeatherIconState = ViewState(ViewVisibility.GONE),
            tvForecastCityNameState = TextViewState(ViewVisibility.GONE),
            tvForecastDescriptionState = TextViewState(ViewVisibility.GONE),
            tvForecastTemperatureState = TextViewState(ViewVisibility.GONE),
            tvForecastErrorMessageState = TextViewState(ViewVisibility.GONE),
            pbForecastLoadingState = ViewState(ViewVisibility.VISIBLE)
        )
    }

    private fun onError(throwable: Throwable) {
        forecastLiveData.value = ForecastScreenState(
            llForecastContainerState = ViewState(background = getBackgroundColor("")),
            ivForecastWeatherIconState = ViewState(ViewVisibility.GONE),
            tvForecastCityNameState = TextViewState(ViewVisibility.GONE),
            tvForecastDescriptionState = TextViewState(ViewVisibility.GONE),
            tvForecastTemperatureState = TextViewState(ViewVisibility.GONE),
            tvForecastErrorMessageState = TextViewState(text = "Erro ao carregar cidade"),
            pbForecastLoadingState = ViewState(ViewVisibility.GONE)
        )
    }

    private fun onRequestSuccess(response: Response<Forecast>) {
        val forecast = response.body()

        forecast?.let {
            forecastLiveData.value = ForecastScreenState(
                llForecastContainerState = ViewState(background = getBackgroundColor(forecast.periodOfTheDay)),
                ivForecastWeatherIconState = ViewState(background = getWeatherIcon(forecast.weather)),
                tvForecastCityNameState = TextViewState(text = forecast.cityName),
                tvForecastDescriptionState = TextViewState(text = forecast.forecastDesc),
                tvForecastTemperatureState = TextViewState(text = forecast.temperature),
                tvForecastErrorMessageState = TextViewState(ViewVisibility.GONE),
                pbForecastLoadingState = ViewState(ViewVisibility.GONE)
            )
        }
    }

    private fun getWeatherIcon(weather: String): Int {
        return when(weather) {
            "sunny" -> R.drawable.ic_sunny
            "raining" -> R.drawable.ic_rain
            else -> R.drawable.ic_moon
        }
    }

    private fun getBackgroundColor(periodOfTheDay: String): Int {
        return when(periodOfTheDay) {
            "morning" -> R.color.clear_day_bg
            "night" -> R.color.clear_night_bg
            else -> R.color.white
        }
    }
}