package popmovies.com.example.android.baking_app;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.parceler.Parcels;

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
    @BindView(R.id.next_step_button) Button nextButton;
    @BindView(R.id.previous_step_button) Button prevButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);

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