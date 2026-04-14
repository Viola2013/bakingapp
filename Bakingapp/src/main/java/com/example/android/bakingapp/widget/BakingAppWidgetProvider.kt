package com.example.android.bakingapp.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import com.example.android.bakingapp.R
import com.example.android.bakingapp.data.RecipePreferences

class BakingAppWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (AppWidgetManager.ACTION_APPWIDGET_UPDATE == intent.action) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val componentName = ComponentName(context, BakingAppWidgetProvider::class.java)
            appWidgetManager.notifyAppWidgetViewDataChanged(
                appWidgetManager.getAppWidgetIds(componentName),
                R.id.widget_ingredient_list
            )
            onUpdate(context, appWidgetManager, appWidgetManager.getAppWidgetIds(componentName))
        }
    }

    companion object {
        fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val recipeName = RecipePreferences.getRecipeName(context)
            val views = RemoteViews(context.packageName, R.layout.baking_widget)
            views.setTextViewText(R.id.widget_recipe_name, recipeName)

            val intent = Intent(context, BakingWidgetService::class.java)
            views.setRemoteAdapter(R.id.widget_ingredient_list, intent)
            views.setEmptyView(R.id.widget_ingredient_list, R.id.widget_recipe_name)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
