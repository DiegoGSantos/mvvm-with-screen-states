package com.diego.mvvmwithscreenstates.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.diego.mvvmwithscreenstates.R
import com.diego.mvvmwithscreenstates.extension.hideKeyboard
import com.diego.mvvmwithscreenstates.view_model.ForecastViewModel
import com.diego.screenstatesarch.view_state.setTextViewState
import com.diego.mvvmwithscreenstates.view_state.setViewState
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel = ForecastViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setObserver()

        btSearchCity.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                viewModel.getForecast(etSearchCity.text.toString())
            }
            hideKeyboard()
        }
    }

    private fun setObserver() {
        viewModel.forecastLiveData.observe(this, Observer<ForecastScreenState> { forecastScreenState ->
            forecastScreenState?.let {
                llForecastContainer.setViewState(forecastScreenState.container)
                ivForecastWeatherIcon.setViewState(forecastScreenState.weatherIcon)
                tvForecastCityName.setTextViewState(forecastScreenState.cityName)
                tvForecastDescription.setTextViewState(forecastScreenState.description)
                tvForecastTemperature.setTextViewState(forecastScreenState.temperature)
                tvForecastErrorMessage.setTextViewState(forecastScreenState.errorMessage)
                pbForecastLoading.setViewState(forecastScreenState.loading)
            }
        })
    }
}
