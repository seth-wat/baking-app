package popmovies.com.example.android.baking_app.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import popmovies.com.example.android.baking_app.R;
import popmovies.com.example.android.baking_app.data.Ingredient;
import popmovies.com.example.android.baking_app.data.Recipe;
import popmovies.com.example.android.baking_app.data.Step;

/**
 * Adapter used to populate the ingredients for the
 * homescreen widget
 */

public class WidgetListAdapter extends ArrayAdapter<Ingredient> {

    WidgetListAdapter(Context context, int resource, List<Ingredient> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        /*
        Set the TextView text to the name of the ingredient.
         */
        Ingredient ingredient = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_ingredient, parent, false);
        }
        TextView ingredientView = (TextView) parent.findViewById(R.id.widget_recipe_name_text_view);
        ingredientView.setText(ingredient.getIngredient());
        return convertView;
    }
}
