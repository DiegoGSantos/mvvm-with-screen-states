package com.diego.mvvmwithscreenstates.rest_client

import com.diego.mvvmwithscreenstates.model.Forecast
import com.diego.mvvmwithscreenstates.model.Task
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call

interface Service {

    @retrofit2.http.GET("/")
    fun listTasks(): Call<List<Task>>

    @retrofit2.http.GET("/forecast")
    fun getForecast(@retrofit2.http.Query("city") city: String): Call<Forecast>

    companion object Factory {
        fun create(): Service {

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()

            httpClient.addInterceptor(logging)

            val retrofit = retrofit2.Retrofit.Builder()
                    .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                    .client(httpClient.build())
                    .baseUrl(Constants.BASE_URL)
                    .build()

            return retrofit.create(Service::class.java);
        }
    }
}
