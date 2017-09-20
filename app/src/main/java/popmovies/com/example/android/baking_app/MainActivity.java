package popmovies.com.example.android.baking_app;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import popmovies.com.example.android.baking_app.data.Recipe;
import popmovies.com.example.android.baking_app.utils.JsonUtils;
import popmovies.com.example.android.baking_app.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
               return NetworkUtils.getResponseFromURL(NetworkUtils.urlFromString(NetworkUtils.URL_FOR_JSON));
            }

            @Override
            protected void onPostExecute(String s) {
                TextView tv = (TextView) findViewById(R.id.only_text_view);
                ArrayList<Recipe> r = JsonUtils.parseRecipes(s);
                    tv.setText(r.get(0).getSteps().get(1 ).getShortDescription() + " " + r.get(1).getName() + " " + r.get(2).getName() + " " + r.get(3).getName());
            }
        }.execute();
    }
}
