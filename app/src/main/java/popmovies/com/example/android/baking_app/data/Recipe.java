package popmovies.com.example.android.baking_app.data;

import org.parceler.Parcel;

import java.util.List;

/**
 * POJO for JSON data
 */

@Parcel
public class Recipe {


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    int id;
    String name;
    int servings;
    String image;
    List<Ingredient> ingredients;
    List<Step> steps;

    public Recipe(int id, String name, int servings, String image, List<Ingredient> ingredients, List<Step> steps) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.image = image;
        this.ingredients = ingredients;
        this.steps = steps;
    }
}
