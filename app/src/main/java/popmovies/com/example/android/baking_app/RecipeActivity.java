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
import popmovies.com.example.android.baking_app.fragments.StepFragment;

public class RecipeActivity extends AppCompatActivity implements
    RecipeFragment.onStepSelectedListener {

    public static final String EXTRA_STEPS = "extra_steps";
    public static final String EXTRA_STEP_POSITION = "extra_step_position";
    public static final String EXTRA_RECIPE_NAME = "extra_recipe_name";
    Recipe recipe;
    boolean isTabletMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        /*
        If step_fragment_container exists then we are in tablet layout
        with the sw600dp.
         */
        if (findViewById(R.id.step_fragment_container) != null) isTabletMode = true;

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
        if (!isTabletMode) {
            /*
            If we are not in tablet mode then just add the RecipeFragment
            to the Activity.
             */
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_fragment_container, recipeFragment)
                    .commit();
        } else {
            //we are in tablet mode
            StepFragment stepFragment = new StepFragment();
            stepFragment.setStep(recipe.getSteps().get(0));
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_fragment_container, recipeFragment)
                    .add(R.id.step_fragment_container, stepFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipes_menu, menu);
        return true;
    }

    @Override
    public void onStepSelected(List<Step> steps, int position) {
        if (!isTabletMode) {
            //Pass data to StepActivity
            Intent intent = new Intent(this, StepActivity.class);
            intent.putExtra(EXTRA_STEPS, Parcels.wrap(steps));
            intent.putExtra(EXTRA_STEP_POSITION, position);
            intent.putExtra(EXTRA_RECIPE_NAME, recipe.getName());
            startActivity(intent);
        } else {
            /*
            We are in table mode and need to create a new StepFragment
            to pass in the selected step and replace it in this Activity
             */
            StepFragment stepFragment = new StepFragment();
            stepFragment.setStep(recipe.getSteps().get(position));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_fragment_container, stepFragment)
                    .commit();
        }
    }
}
