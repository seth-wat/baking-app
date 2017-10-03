package popmovies.com.example.android.baking_app.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import popmovies.com.example.android.baking_app.MainActivity;
import popmovies.com.example.android.baking_app.R;
import popmovies.com.example.android.baking_app.RecipeActivity;
import popmovies.com.example.android.baking_app.data.Ingredient;
import popmovies.com.example.android.baking_app.data.Recipe;
import popmovies.com.example.android.baking_app.services.ListWidgetService;

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
        /*
        If I wanted to handle resizing
        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
         */

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        if (passedRecipe != null) {

            views.setTextViewText(R.id.widget_recipe_name_text_view, passedRecipe.getName());
            /*
            Loop over the ingredients and add them to a List to make passing them
            through adapterIntent easier.
             */
            ArrayList<String> ingredientToPass = new ArrayList<>();

            for (Ingredient ing : passedRecipe.getIngredients()) {
                ingredientToPass.add(ing.getIngredient());
            }

            /*
            Set the remote adapter.
             */
            Intent adapterIntent = new Intent(context, ListWidgetService.class);
            adapterIntent.putExtra("ingredientBundle", ingredientToPass);
            views.setRemoteAdapter(R.id.ingredient_list_view, adapterIntent);

            /*
            updateRemoteViewsIntent broadcast the newly selected recipe ingredients to
            the remote adapter. It is done in this way because setRemoteAdapter
            does not update.
             */
            Intent updateRemoteViewsIntent = new Intent("update_me");
            updateRemoteViewsIntent.putExtra("ingredientBundle", ingredientToPass);
            context.sendBroadcast(updateRemoteViewsIntent);

            /*
            After broadcasting invalidate the old data.
             */
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.ingredient_list_view);


        } else {
            views.setTextViewText(R.id.widget_recipe_name_text_view, "Please select a recipe!");
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

