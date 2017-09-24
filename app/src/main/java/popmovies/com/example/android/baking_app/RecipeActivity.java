package popmovies.com.example.android.baking_app;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import popmovies.com.example.android.baking_app.data.Recipe;
import popmovies.com.example.android.baking_app.data.Step;
import popmovies.com.example.android.baking_app.eventlisteners.MainRecipeClickListener;
import popmovies.com.example.android.baking_app.fragments.RecipeFragment;

public class RecipeActivity extends AppCompatActivity implements
    RecipeFragment.onStepSelectedListener {

    public static final String EXTRA_STEPS = "extra_steps";
    public static final String EXTRA_STEP_POSITION = "extra_step_position";
    public static final String EXTRA_RECIPE_NAME = "extra_recipe_name";
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
        getSupportActionBar().setTitle(recipe.getName());
        
        //Create a new recipe fragment and pass in the recipe we just retrieved.
        RecipeFragment recipeFragment = new RecipeFragment();
        recipeFragment.setRecipe(recipe);
        recipeFragment.setmCallback(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recipe_fragment_container, recipeFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipes_menu, menu);
        return true;
    }

    @Override
    public void onStepSelected(List<Step> steps, int position) {
        //Pass data to StepActivity
        Intent intent = new Intent(this, StepActivity.class);
        intent.putExtra(EXTRA_STEPS, Parcels.wrap(steps));
        intent.putExtra(EXTRA_STEP_POSITION, position);
        intent.putExtra(EXTRA_RECIPE_NAME, recipe.getName());
        startActivity(intent);
    }
}
