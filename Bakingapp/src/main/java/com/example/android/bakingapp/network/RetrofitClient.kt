package com.example.android.bakingapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://d17h27t6h515a5.cloudfront.net/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val recipeService: RecipeService by lazy {
        retrofit.create(RecipeService::class.java)
    }
}
