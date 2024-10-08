package com.ttv.myshoppinglist

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://maps.googleapis.com/"

    fun create():GeocodingApiService
    {
        val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit.create(GeocodingApiService::class.java)
    }
}