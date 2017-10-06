package popmovies.com.example.android.baking_app;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import popmovies.com.example.android.baking_app.data.Recipe;
import popmovies.com.example.android.baking_app.eventlisteners.MainRecipeClickListener;
import popmovies.com.example.android.baking_app.loaders.RecipeLoader;
import popmovies.com.example.android.baking_app.utils.JsonUtils;
import popmovies.com.example.android.baking_app.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    @BindView(R.id.recipe_one) FrameLayout recipeOneFrame;
    @BindView(R.id.recipe_two) FrameLayout recipeTwoFrame;
    @BindView(R.id.recipe_three) FrameLayout recipeThreeFrame;
    @BindView(R.id.recipe_four) FrameLayout recipeFourFrame;
    @BindView(R.id.recipe_one_text_view) TextView recipeOneTextView;
    @BindView(R.id.recipe_two_text_view)TextView recipeTwoTextView;
    @BindView(R.id.recipe_three_text_view)TextView recipeThreeTextView;
    @BindView(R.id.recipe_four_text_view)TextView recipeFourTextView;
    @BindView(R.id.main_parent) LinearLayout parentLinearLayout;
    @BindView(R.id.main_progress_bar) ProgressBar  mainProgressBar;
    @BindView(R.id.error_text_view) TextView errorTextView;

    public static final int RECIPE_LOADER = 123;
    ArrayList<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle(getString(R.string.recipe_selection));
        //Initialize the loader to fetch/parse the recipes.
        LoaderManager loaderManager = getSupportLoaderManager();
        if (NetworkUtils.hasInternet(this)) {
            loaderManager.initLoader(RECIPE_LOADER, null, this);
        } else {
            mainProgressBar.setVisibility(View.GONE);
            errorTextView.setVisibility(View.VISIBLE);
            errorTextView.setText(getString(R.string.error_message));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_main_activity) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        if(id == RECIPE_LOADER) {
            return new RecipeLoader(this);
        }
        return null;
    }


    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        mainProgressBar.setVisibility(View.GONE);
        errorTextView.setVisibility(View.GONE);
        parentLinearLayout.setVisibility(View.VISIBLE);
        if (data.length() <= 0) {
            recipeOneTextView.setText(getString(R.string.error_message));
        }
        recipes = JsonUtils.parseRecipes(data);
        /*
        Set each recipe text view to it's corresponding recipe name and set
        the onClickListener.
         */
        recipeOneTextView.setText(recipes.get(0).getName());
        recipeOneFrame.setOnClickListener(new MainRecipeClickListener(recipes.get(0), this));

        recipeTwoTextView.setText(recipes.get(1).getName());
        recipeTwoFrame.setOnClickListener(new MainRecipeClickListener(recipes.get(1), this));

        recipeThreeTextView.setText(recipes.get(2).getName());
        recipeThreeFrame.setOnClickListener(new MainRecipeClickListener(recipes.get(2), this));

        recipeFourTextView.setText(recipes.get(3).getName());
        recipeFourFrame .setOnClickListener(new MainRecipeClickListener(recipes.get(3), this));
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
