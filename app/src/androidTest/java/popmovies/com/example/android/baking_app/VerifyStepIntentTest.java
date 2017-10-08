package popmovies.com.example.android.baking_app;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.StringStartsWith.startsWith;

/**
 *
 */
@RunWith(AndroidJUnit4.class)
public class VerifyStepIntentTest {
    public static final String RECIPE_NAME_NUTELLA = "Nutella Pie";
    public static final String FIRST_ITEM = "0.";
    @Rule
    public IntentsTestRule<MainActivity> testRule = new IntentsTestRule(MainActivity.class);

    @Test
    public void clickStep_LaunchStepFragmet_VerifyIntent() {
        /*
        Selects and clicks the first item out of the RecyclerView that contains steps
        in RecipeFragment. Does this by reading the text of the item used to populate
        the RecyclerView. Verifies the Intent extras.
         */
        onView(withText(RECIPE_NAME_NUTELLA)).perform(click());
        onView(allOf(withId(R.id.step_text_view), withText(startsWith(FIRST_ITEM))))
                .perform(click());

        intended(allOf(
                hasExtra(RecipeActivity.EXTRA_STEP_POSITION, 0),
                hasExtra(RecipeActivity.EXTRA_RECIPE_NAME, RECIPE_NAME_NUTELLA)
        ));
    }
}
