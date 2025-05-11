package com.example.fishka.retrofit

import com.example.fishka.api.FishService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.inaturalist.org/"

    val api: FishService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FishService::class.java)
    }
}
