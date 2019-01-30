package com.diego.mvvmwithscreenstates.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.diego.mvvmwithscreenstates.R
import com.diego.mvvmwithscreenstates.model.Forecast
import com.diego.mvvmwithscreenstates.model.Task
import com.diego.mvvmwithscreenstates.view_model.ForecastViewModel
import com.diego.mvvmwithscreenstates.view_model.TasksViewModel
import com.diego.mvvmwithscreenstates.view_state.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val viewModel = ForecastViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setObserver()

        viewModel.getForecast("Campinas")
    }

    private fun setObserver() {
        viewModel.forecastLiveData.observe(this, Observer<ForecastScreenState> { forecastScreenState ->
            forecastScreenState?.let {
                llForecastContainer.setViewState(forecastScreenState.llForecastContainerState)
                ivForecastWeatherIcon.setViewState(forecastScreenState.ivForecastWeatherIconState)
                tvForecastCityName.setTextViewState(forecastScreenState.tvForecastCityNameState)
                tvForecastDescription.setTextViewState(forecastScreenState.tvForecastDescriptionState)
                tvForecastTemperature.setTextViewState(forecastScreenState.tvForecastTemperatureState)
                tvForecastErrorMessage.setViewState(forecastScreenState.tvForecastErrorMessageState)
                pbForecastLoading.setViewState(forecastScreenState.pbForecastLoadingState)
            }
        })
    }
}
