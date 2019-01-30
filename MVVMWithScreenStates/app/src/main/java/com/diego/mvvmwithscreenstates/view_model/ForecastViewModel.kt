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

    fun getForecast(city: String) {
//        GlobalScope.launch(Dispatchers.Main) {
//            Service.create().getForecast(city).awaitResponse(::onRequestSuccess, ::onError)
//        }

        GlobalScope.launch(Dispatchers.Main) {
            val result = Service.create().getForecast(city).awaitResult()

            when (result) {
                is Result.Success -> onRequestSuccess(result.data)
                is Result.Error -> onError(result.throwable)
            }
        }
    }

    private fun onError(throwable: Throwable) {

    }

    private fun onRequestSuccess(response: Response<Forecast>) {
        val forecast = response.body()

        forecast?.let {
            forecastLiveData.value = ForecastScreenState(
                ViewState(background = getBackgroundColor(forecast.periodOfTheDay)),
                ViewState(background = getWeatherIcon(forecast.weather)),
                TextViewState(text = forecast.cityName),
                TextViewState(text = forecast.forecastDesc),
                TextViewState(text = forecast.temperature),
                TextViewState(ViewVisibility.GONE),
                ViewState(ViewVisibility.GONE)
            )
        }
    }

    private fun getWeatherIcon(weather: String): Int {
        return when(weather) {
            "sunny" -> R.drawable.ic_sunny
            else -> R.drawable.ic_moon
        }
    }

    private fun getBackgroundColor(periodOfTheDay: String): Int {
        return when(periodOfTheDay) {
            "morning" -> R.color.clear_day_bg
            else -> R.color.white
        }
    }
}