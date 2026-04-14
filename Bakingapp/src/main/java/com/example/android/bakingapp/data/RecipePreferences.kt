package com.example.android.bakingapp.data

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.android.bakingapp.model.Ingredient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.core.content.edit

object RecipePreferences {
    private const val PREFS_NAME = "baking_prefs"
    private const val KEY_INGREDIENTS = "key_ingredients"
    private const val KEY_RECIPE_NAME = "key_recipe_name"

    fun saveIngredients(context: Context, recipeName: String, ingredients: List<Ingredient>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = gson.toJson(ingredients)
        prefs.edit {
            putString(KEY_RECIPE_NAME, recipeName)
                .putString(KEY_INGREDIENTS, json)
        }
    }

    fun getIngredients(context: Context): List<Ingredient> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_INGREDIENTS, null) ?: return emptyList()
        val type = object : TypeToken<List<Ingredient>>() {}.type
        return Gson().fromJson(json, type)
    }

    fun getRecipeName(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_RECIPE_NAME, "No recipe selected") ?: ""
    }
}
