package com.example.android.bakingapp.network

import com.example.android.bakingapp.model.Recipe
import retrofit2.http.GET

interface RecipeService {
    @GET("topher/2017/May/59121517_baking/baking.json")
    suspend fun getRecipes(): List<Recipe>
}
