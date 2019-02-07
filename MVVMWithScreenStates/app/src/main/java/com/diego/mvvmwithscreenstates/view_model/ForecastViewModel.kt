package com.diego.mvvmwithscreenstates.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diego.mvvmwithscreenstates.R
import com.diego.mvvmwithscreenstates.model.Forecast
import com.diego.mvvmwithscreenstates.rest_client.*
import com.diego.mvvmwithscreenstates.view.ForecastScreenState
import com.diego.mvvmwithscreenstates.view_state.*
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
        val forecastScreenState = ForecastScreenState().apply {
            container.withBackground(getBackgroundColor(""))
            weatherIcon.remove()
            cityName.remove()
            description.remove()
            temperature.remove()
            errorMessage.remove()
            loading.show()
        }

        updateState(forecastScreenState)
    }

    private fun onError(throwable: Throwable) {
        val forecastScreenState = ForecastScreenState().apply {
            container.withBackground(getBackgroundColor(""))
            weatherIcon.remove()
            cityName.remove()
            description.remove()
            temperature.remove()
            errorMessage.hasText("Erro ao carregar cidade").show()
            loading.remove()
        }

        updateState(forecastScreenState)
    }

    private fun onRequestSuccess(response: Response<Forecast>) {
        val forecast = response.body()

        forecast?.let {
            val forecastScreenState = ForecastScreenState().apply {
                container.withBackground(getBackgroundColor(forecast.periodOfTheDay))
                weatherIcon.withBackground(getWeatherIcon(forecast.weather))
                cityName.hasText(forecast.cityName)
                description.hasText(forecast.forecastDesc)
                temperature.hasText(forecast.temperature)
                errorMessage.remove()
                loading.remove()
            }

            updateState(forecastScreenState)
        }
    }

    private fun updateState(forecastScreenState: ForecastScreenState) {
        forecastLiveData.postValue(forecastScreenState)
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