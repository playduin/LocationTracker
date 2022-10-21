package playduin.locationtracker.ui.tracker;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.orhanobut.hawk.Hawk;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import playduin.locationtracker.App;
import playduin.locationtracker.R;
import playduin.locationtracker.models.TestAppModule;
import playduin.locationtracker.ui.TrackerActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class TestAutoLogin {
    @Rule
    public ActivityScenarioRule<TrackerActivity> activityRule = new ActivityScenarioRule<>(TrackerActivity.class);

    @BeforeClass
    public static void setupAppModule() {
        App.getInstance().setAppModule(new TestAppModule(App.getInstance().getApplicationContext()));
        Hawk.put("token", "It_is_token!");
    }

    @Test
    public void testAutoLogin() {
        onView(withId(R.id.tracker)).check(matches(isDisplayed()));
    }
}
