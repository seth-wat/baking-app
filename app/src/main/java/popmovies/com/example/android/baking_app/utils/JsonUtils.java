package popmovies.com.example.android.baking_app.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import popmovies.com.example.android.baking_app.data.Ingredient;
import popmovies.com.example.android.baking_app.data.Recipe;
import popmovies.com.example.android.baking_app.data.Step;

/**
 * This class is used to parse the recipe JSON that's it.
 */

public final class JsonUtils {

    private JsonUtils() {

    }

    //Call this method with the data retrieved from NetworkUtils
    public static ArrayList<Recipe> parseRecipes(String rawData) {

        ArrayList<Recipe> recipes = new ArrayList<>();
        try {

            JSONArray root = new JSONArray(rawData);

            for (int i = 0; i < root.length(); i++) {
                JSONObject recipe = root.getJSONObject(i);
                JSONArray ingredients = recipe.getJSONArray("ingredients");
                JSONArray steps = recipe.getJSONArray("steps");
                ArrayList<Ingredient> pojoIngredients = new ArrayList<>();
                ArrayList<Step> pojoSteps = new ArrayList<>();


                //loop over the ingredients for this recipe and add them to a list
                for (int j = 0; j < ingredients.length(); j++) {
                    JSONObject ingredient = ingredients.getJSONObject(j);
                    pojoIngredients.add(new Ingredient(
                            ingredient.getDouble("quantity"),
                            ingredient.getString("measure"),
                            ingredient.getString("ingredient")
                    ));
                }


                //loop over the steps for this recipe and add them to a list
                for (int k = 0; k < steps.length(); k++) {
                    JSONObject step = steps.getJSONObject(k);
                    pojoSteps.add(new Step(
                            step.getInt("id"),
                            step.getString("shortDescription"),
                            step.getString("description"),
                            step.getString("videoURL"),
                            step.getString("thumbnailURL")
                    ));
                }
                //Create the recipe object and add it to the recipe list
                recipes.add(new Recipe(
                        recipe.getInt("id"),
                        recipe.getString("name"),
                        recipe.getInt("servings"),
                        recipe.getString("image"),
                        pojoIngredients,
                        pojoSteps
                ));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipes;
    }


}
