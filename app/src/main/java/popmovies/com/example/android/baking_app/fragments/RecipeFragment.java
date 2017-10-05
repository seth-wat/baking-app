package popmovies.com.example.android.baking_app.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import popmovies.com.example.android.baking_app.R;
import popmovies.com.example.android.baking_app.adapters.StepAdapter;
import popmovies.com.example.android.baking_app.data.Recipe;
import popmovies.com.example.android.baking_app.data.Step;

/**
 * This fragment contains a list of ingredients and steps
 * when a step is clicked it will pass data to the Fragment
 * that displays step details.
 */
public class RecipeFragment extends Fragment {


    private Recipe recipe;
    private onStepSelectedListener mCallback;
    private Unbinder unbinder;
    private StepAdapter stepAdapter;
    private boolean isTablet = false;
    private int positionToRestore;

    /*
    Constants for saving state.
    */
    public static final String IS_TABLET_OUT = "is_tablet_out";
    public static final String RECIPE_OUT = "recipe_out";
    public static final String POSITION_OUT = "position_out";

    @BindView(R.id.ingredients_text_view) TextView ingredientsTextView;
    @BindView(R.id.steps_recycler_view) RecyclerView stepsRecyclerView;


    public RecipeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(RECIPE_OUT)) {
                recipe = Parcels.unwrap((Parcelable) savedInstanceState.get(RECIPE_OUT));
                isTablet = (Boolean) savedInstanceState.get(IS_TABLET_OUT);
                mCallback = (onStepSelectedListener) getContext();
                positionToRestore = (Integer) savedInstanceState.get(POSITION_OUT);
            }
        }
        //Loop over the ingredient list and add them to the TextView.
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            if (i == 0) {
                ingredientsTextView.setText("- " + recipe.getIngredients().get(i).getIngredient());
            } else {
                ingredientsTextView.setText(ingredientsTextView.getText() + "\n" +
                        "- " + recipe.getIngredients().get(i).getIngredient());
            }
        }

        /*
        Set the layout manager/adapter for the RecyclerView and pass in the steps
        as well as the onStepSelectedListener implemented in RecipeActivity to
        facilitate communication between this fragment and the host activity.
         */
        LinearLayoutManager stepLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        stepsRecyclerView.setLayoutManager(stepLayoutManager);
        stepAdapter = new StepAdapter(getContext(), recipe.getSteps(), mCallback, isTablet, positionToRestore);
        stepsRecyclerView.setAdapter(stepAdapter);

        return rootView;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public interface onStepSelectedListener {
        void onStepSelected(List<Step> steps, int position);
    }

    public void setmCallback(onStepSelectedListener mCallback) {
        this.mCallback = mCallback;
    }

    public void setTabletMode(boolean bool) {
        isTablet = bool;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(IS_TABLET_OUT, isTablet);
        outState.putParcelable(RECIPE_OUT, Parcels.wrap(recipe));
        outState.putInt(POSITION_OUT, stepAdapter.getLastPosition());
        super.onSaveInstanceState(outState);
    }

}
