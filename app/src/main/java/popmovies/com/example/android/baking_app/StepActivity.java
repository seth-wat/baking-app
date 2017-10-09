package popmovies.com.example.android.baking_app;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import popmovies.com.example.android.baking_app.data.Step;
import popmovies.com.example.android.baking_app.fragments.StepFragment;

/*
    This activity houses all steps for the selected recipe.
    You can cycle through steps using the next/prev buttons
    which replaces the fragment based on the position stored
    in stepPosition
 */

public class StepActivity extends AppCompatActivity {

    int stepPosition;
    StepFragment stepFragment;
    List<Step> steps;
    String recipeName;
    @BindView(R.id.next_step_button)
    Button nextButton;
    @BindView(R.id.previous_step_button)
    Button prevButton;

    public static final String STEPS_OUT = "steps_out";
    public static final String STEP_POSITION_OUT = "step_position_out";
    public static final String RECIPE_NAME_OUT = "recipe_name_out";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null) {
            steps =  Parcels.unwrap((Parcelable) savedInstanceState.get(STEPS_OUT));
            stepPosition = (Integer) savedInstanceState.get(STEP_POSITION_OUT);
            recipeName = (String) savedInstanceState.get(RECIPE_NAME_OUT);

            //Handles replacing the Fragment with the next step.
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (stepPosition + 1 >= steps.size()) {
                        nextButton.setEnabled(false);
                        return;
                    }
                    prevButton.setEnabled(true);

                    stepFragment = new StepFragment();
                    stepPosition = (stepPosition + 1);
                    if (steps.size() - 1 == stepPosition) nextButton.setEnabled(false);
                    stepFragment.setStep(steps.get(stepPosition));
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container_frame, stepFragment)
                            .commit();
                    getSupportActionBar().setTitle(recipeName + " - Step #" + Integer.toString(stepPosition));
                }
            });

            //Handles replacing the Fragment with the previous step.
            prevButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (stepPosition - 1 < 0) {
                        prevButton.setEnabled(false);
                        return;
                    }
                    nextButton.setEnabled(true);

                    stepFragment = new StepFragment();
                    stepPosition = (stepPosition - 1);
                    if (stepPosition == 0) prevButton.setEnabled(false);
                    stepFragment.setStep(steps.get(stepPosition));
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container_frame, stepFragment)
                            .commit();
                    getSupportActionBar().setTitle(recipeName + " - Step #" + Integer.toString(stepPosition));
                }

            });
        } else {
            //Get all the data we need out of the Intent that started the activity.
            steps = Parcels.unwrap(getIntent().getParcelableExtra(RecipeActivity.EXTRA_STEPS));
            stepPosition = getIntent().getIntExtra(RecipeActivity.EXTRA_STEP_POSITION, 0);
            recipeName = getIntent().getStringExtra(RecipeActivity.EXTRA_RECIPE_NAME);

            //Set the proper ActionBar title;
            getSupportActionBar().setTitle(recipeName + " - Step #" + Integer.toString(stepPosition));

            //Create the Fragment.
            stepFragment = new StepFragment();
            stepFragment.setStep(steps.get(stepPosition));

            //Execute the FragmentTransaction.
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.container_frame, stepFragment)
                    .commit();


            //Handles replacing the Fragment with the next step.
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (stepPosition + 1 >= steps.size()) {
                        nextButton.setEnabled(false);
                        return;
                    }
                    prevButton.setEnabled(true);

                    stepFragment = new StepFragment();
                    stepPosition = (stepPosition + 1);
                    if (steps.size() - 1 == stepPosition) nextButton.setEnabled(false);
                    stepFragment.setStep(steps.get(stepPosition));
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container_frame, stepFragment)
                            .commit();
                    getSupportActionBar().setTitle(recipeName + " - Step #" + Integer.toString(stepPosition));
                }
            });

            //Handles replacing the Fragment with the previous step.
            prevButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (stepPosition - 1 < 0) {
                        prevButton.setEnabled(false);
                        return;
                    }
                    nextButton.setEnabled(true);

                    stepFragment = new StepFragment();
                    stepPosition = (stepPosition - 1);
                    if (stepPosition == 0) prevButton.setEnabled(false);
                    stepFragment.setStep(steps.get(stepPosition));
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container_frame, stepFragment)
                            .commit();
                    getSupportActionBar().setTitle(recipeName + " - Step #" + Integer.toString(stepPosition));
                }

            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_main_activity) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(STEPS_OUT, Parcels.wrap(steps));
        outState.putInt(STEP_POSITION_OUT, stepPosition);
        outState.putString(RECIPE_NAME_OUT, recipeName);
        super.onSaveInstanceState(outState);
    }
}
