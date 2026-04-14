package com.example.android.bakingapp.widget

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.android.bakingapp.R
import com.example.android.bakingapp.data.RecipePreferences
import com.example.android.bakingapp.model.Ingredient

class BakingWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return BakingWidgetItemFactory(applicationContext)
    }
}

class BakingWidgetItemFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private var ingredients: List<Ingredient> = emptyList()

    override fun onCreate() {}

    override fun onDataSetChanged() {
        ingredients = RecipePreferences.getIngredients(context)
    }

    override fun onDestroy() {}

    override fun getCount(): Int = ingredients.size

    override fun getViewAt(position: Int): RemoteViews {
        val ingredient = ingredients[position]
        val views = RemoteViews(context.packageName, R.layout.widget_item)
        views.setTextViewText(
            R.id.widget_ingredient_text,
            "${ingredient.quantity} ${ingredient.measure} ${ingredient.ingredient}"
        )
        return views
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = position.toLong()

    override fun hasStableIds(): Boolean = true
}
