package popmovies.com.example.android.baking_app.data;

import org.parceler.Parcel;

/**
 * POJO for JSON data
 */
@Parcel
public class Ingredient {

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    double quantity;
    String measure;
    String ingredient;

    public Ingredient(double quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }
}
