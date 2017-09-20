package popmovies.com.example.android.baking_app.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import popmovies.com.example.android.baking_app.utils.NetworkUtils;

/**
 * Really basic just fetches the JSON using NetworkUtils
 */

public class RecipeLoader extends AsyncTaskLoader<String>{

    public RecipeLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        return NetworkUtils.getResponseFromURL(NetworkUtils.urlFromString(NetworkUtils.URL_FOR_JSON));
    }
}
