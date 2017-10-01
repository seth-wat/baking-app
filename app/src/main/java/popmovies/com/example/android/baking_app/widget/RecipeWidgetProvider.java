package popmovies.com.example.android.baking_app.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RemoteViews;

import org.parceler.Parcels;

import popmovies.com.example.android.baking_app.MainActivity;
import popmovies.com.example.android.baking_app.R;
import popmovies.com.example.android.baking_app.RecipeActivity;
import popmovies.com.example.android.baking_app.data.Recipe;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {
    public Recipe recipe;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.hasExtra(RecipeActivity.EXTRA_RECIPE)) {
            recipe = Parcels.unwrap(intent.getParcelableExtra(RecipeActivity.EXTRA_RECIPE));
        }
        super.onReceive(context, intent);
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Recipe passedRecipe) {
        CharSequence widgetText = "Testing";
        /*
        If I wanted to handle resizing
        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
         */

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        if (passedRecipe != null) {
            views.setTextViewText(R.id.widget_recipe_name_text_view, passedRecipe.getName());
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.ingredient_list_view);

        } else {
            views.setTextViewText(R.id.widget_recipe_name_text_view, widgetText);
        }

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widget_recipe_name_text_view, pendingIntent);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipe);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

