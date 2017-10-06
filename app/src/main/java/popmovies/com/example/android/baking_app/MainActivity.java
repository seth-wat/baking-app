package popmovies.com.example.android.baking_app;

import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
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

    public static final int RECIPE_LOADER = 123;
    ArrayList<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Initialize the loader to fetch/parse the recipes.
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(RECIPE_LOADER, null, this);
        getSupportActionBar().setTitle("Recipe Selection");

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
        if (data.length() <= 0) {
            recipeOneTextView.setText("Error");
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
