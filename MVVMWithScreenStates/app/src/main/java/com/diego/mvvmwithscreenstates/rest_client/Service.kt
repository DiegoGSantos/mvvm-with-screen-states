package com.diego.mvvmwithscreenstates.rest_client

import com.diego.mvvmwithscreenstates.model.Task
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call

interface Service {

    @retrofit2.http.GET("/")
    fun listTasks(): Call<List<Task>>

    companion object Factory {
        fun create(): Service {

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()

            httpClient.addInterceptor(logging)

            val retrofit = retrofit2.Retrofit.Builder()
                    .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                    .client(httpClient.build())
                    .baseUrl("http://192.168.1.108:3000/")
                    .build()

            return retrofit.create(Service::class.java);
        }
    }
}
