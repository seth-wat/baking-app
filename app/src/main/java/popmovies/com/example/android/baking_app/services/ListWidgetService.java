package services;

import android.content.Intent;
import android.widget.RemoteViewsService;

import org.parceler.Parcels;

import popmovies.com.example.android.baking_app.RecipeActivity;
import popmovies.com.example.android.baking_app.data.Recipe;

/**
 *
 */

public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Recipe recipe = Parcels.unwrap(intent.getParcelableExtra(RecipeActivity.EXTRA_RECIPE));
        return new WidgetListRemoteViewsFactory(this.getApplicationContext(), recipe.getIngredients());
    }
}
