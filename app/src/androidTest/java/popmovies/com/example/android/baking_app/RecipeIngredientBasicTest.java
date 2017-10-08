package popmovies.com.example.android.baking_app;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringStartsWith.startsWith;

/**
 * Verifies each recipe launches and checks that the ingredients are correct.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeIngredientBasicTest {
    public static final String RECIPE_NAME_NUTELLA = "Nutella Pie";
    public static final String INGREDIENT_TEXT_NUTELLA = "- Graham Cracker crumbs";
    public static final String RECIPE_NAME_BROWNIES = "Brownies";
    public static final String INGREDIENT_TEXT_BROWNIES = "- Bittersweet chocolate";
    public static final String RECIPE_NAME_YELLOW = "Yellow Cake";
    public static final String INGREDIENT_TEXT_YELLOW = "- sifted cake flour";
    public static final String RECIPE_NAME_CHEESECAKE = "Cheesecake";
    public static final String INGREDIENT_TEXT_CHEESECAKE = "- Graham Cracker crumbs";

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickNutellaRecipe_CheckIngredients() {
        onView(withText(RECIPE_NAME_NUTELLA)).perform(click());
        onView(withId(R.id.ingredients_text_view)).check(matches(withText(startsWith(INGREDIENT_TEXT_NUTELLA))));
    }

    @Test
    public void clickBrowniesRecipe_CheckIngredients() {
        onView(withText(RECIPE_NAME_BROWNIES)).perform(click());
        onView(withId(R.id.ingredients_text_view)).check(matches(withText(startsWith(INGREDIENT_TEXT_BROWNIES))));
    }

    @Test
    public void clickYellowCakeRecipe_CheckIngredients() {
        onView(withText(RECIPE_NAME_YELLOW)).perform(click());
        onView(withId(R.id.ingredients_text_view)).check(matches(withText(startsWith(INGREDIENT_TEXT_YELLOW))));
    }

    @Test
    public void clickCheesecakeRecipe_CheckIngredients() {
        onView(withText(RECIPE_NAME_CHEESECAKE)).perform(click());
        onView(withId(R.id.ingredients_text_view)).check(matches(withText(startsWith(INGREDIENT_TEXT_CHEESECAKE))));
    }

}

