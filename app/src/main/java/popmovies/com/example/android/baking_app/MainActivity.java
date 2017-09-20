package popmovies.com.example.android.baking_app;

import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import popmovies.com.example.android.baking_app.data.Recipe;
import popmovies.com.example.android.baking_app.loaders.RecipeLoader;
import popmovies.com.example.android.baking_app.utils.JsonUtils;
import popmovies.com.example.android.baking_app.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{

    public static final int RECIPE_LOADER = 123;

    ArrayList<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize the loader to fetch/parse the recipes.
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(RECIPE_LOADER, null, this);

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
        recipes = JsonUtils.parseRecipes(data);
        Toast.makeText(this, "I parsed the data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
