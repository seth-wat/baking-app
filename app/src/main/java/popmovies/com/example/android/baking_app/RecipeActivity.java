package popmovies.com.example.android.baking_app;

import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Toast;

import org.parceler.Parcel;
import org.parceler.Parcels;

import popmovies.com.example.android.baking_app.data.Recipe;
import popmovies.com.example.android.baking_app.eventlisteners.MainRecipeClickListener;
import popmovies.com.example.android.baking_app.fragments.RecipeFragment;

public class RecipeActivity extends AppCompatActivity {
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        //Grab the recipe out of the intent.
        if (getIntent().hasExtra(MainRecipeClickListener.RECIPE_EXTRA)) {
            recipe = Parcels.unwrap((Parcelable) getIntent()
                    .getExtras()
                    .get(MainRecipeClickListener.RECIPE_EXTRA));
        }

        //Create a new recipe fragment and pass in the recipe we just retrieved.
        RecipeFragment recipeFragment = new RecipeFragment();
        recipeFragment.setRecipe(recipe);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recipe_fragment_container, recipeFragment)
                .commit();
    }

}
