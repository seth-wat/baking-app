package popmovies.com.example.android.baking_app.eventlisteners;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;

import org.parceler.Parcel;
import org.parceler.Parcels;

import popmovies.com.example.android.baking_app.MainActivity;
import popmovies.com.example.android.baking_app.RecipeActivity;
import popmovies.com.example.android.baking_app.data.Recipe;

/**
 * Handles clicks on recipes in MainActivity.
 * Generates the intents to be passed into the fragments.
 */

public class MainRecipeClickListener implements View.OnClickListener {
    public static final String RECIPE_EXTRA = "recipe_extra";
    Recipe recipe;
    Context context;

    public MainRecipeClickListener(Recipe recipe, Context context) {
        this.recipe = recipe;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, RecipeActivity.class);
        Parcelable recipeToPass = Parcels.wrap(recipe);
        intent.putExtra(RECIPE_EXTRA, recipeToPass);
        context.startActivity(intent);
    }
}
