package playduin.locationtracker.ui.tracker;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.orhanobut.hawk.Hawk;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import playduin.locationtracker.R;
import playduin.locationtracker.ui.TrackerActivity;

@RunWith(AndroidJUnit4.class)
public class TestSwitchLoginRegister {
    @Rule
    public ActivityScenarioRule<TrackerActivity> activityRule = new ActivityScenarioRule<>(TrackerActivity.class);

    @BeforeClass
    public static void setupAppModule() {
        Hawk.delete("token");
    }

    @Test
    public void test() {
        onView(withId(R.id.login_fragment)).check(matches(isDisplayed()));

        onView(withText("Register")).perform(click());
        onView(withId(R.id.registration_fragment)).check(matches(isDisplayed()));

        onView(withText("Sign in")).perform(click());
        onView(withId(R.id.login_fragment)).check(matches(isDisplayed()));
    }
}
