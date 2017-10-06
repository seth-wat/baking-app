package popmovies.com.example.android.baking_app;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Checks to make sure all recipes open.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityBasicTest {
    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickRecipeTextView_OpensRecipeActivity_ClickButtonToReturn() {
        onView((withId(R.id.recipe_one_text_view))).perform(click());
        onView((withId(R.id.menu_main_activity))).perform(click());

        onView((withId(R.id.recipe_two_text_view))).perform(click());
        onView((withId(R.id.menu_main_activity))).perform(click());

        onView((withId(R.id.recipe_three_text_view))).perform(click());
        onView((withId(R.id.menu_main_activity))).perform(click());

        onView((withId(R.id.recipe_four_text_view))).perform(click());
        onView((withId(R.id.menu_main_activity))).perform(click());
    }
}
