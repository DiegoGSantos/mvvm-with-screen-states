package com.diego.mvvmwithscreenstates.model

import com.google.gson.annotations.SerializedName

class Forecast(val id: Long,
               @SerializedName("city_name") val cityName: String,
               @SerializedName("forecast_desc") val forecastDesc: String,
               val weather: String,
               @SerializedName("period_of_the_day") val periodOfTheDay: String,
               val temperature: String)
