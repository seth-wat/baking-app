package popmovies.com.example.android.baking_app.services;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
        return new WidgetListRemoteViewsFactory(this.getApplicationContext(), intent.getStringArrayListExtra("ingredientBundle"));
    }
}
