package popmovies.com.example.android.baking_app.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import popmovies.com.example.android.baking_app.R;
import popmovies.com.example.android.baking_app.data.Recipe;

/**
 * This fragment contains a list of ingredients and steps
 * when a step is clicked it will pass data to the Fragment
 * that displays step details.
 */
public class RecipeFragment extends Fragment {


    private Recipe recipe;
    private onStepSelectedListener mCallback;
    private Unbinder unbinder;

    @BindView(R.id.recipe_name_text_view) TextView recipeNameTextView;
    @BindView(R.id.ingredients_text_view) TextView ingredientsTextView;
    @BindView(R.id.steps_recycler_view) RecyclerView stepsRecyclerView;


    public RecipeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        recipeNameTextView.setText(recipe.getName());
        //Loop over the ingredient list and add them to the TextView.
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            ingredientsTextView.setText(ingredientsTextView.getText() + "\n" +
                recipe.getIngredients().get(i).getIngredient());
        }
        return rootView;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
    //check if the container activity implements this interface in onAttach
    public interface onStepSelectedListener {
        void onStepSelected();
    }

//    @Override
//    public void onAttach(Context context) {
//        try {
//            mCallback = (onStepSelectedListener) context;
//        } catch (ClassCastException e) {
//
//        }
//    }
}
