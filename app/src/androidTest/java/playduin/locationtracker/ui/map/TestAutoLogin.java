package playduin.locationtracker.ui.map;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.orhanobut.hawk.Hawk;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import playduin.locationtracker.App;
import playduin.locationtracker.R;
import playduin.locationtracker.models.TestAppModule;
import playduin.locationtracker.models.locations.Location;
import playduin.locationtracker.ui.MapActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class TestAutoLogin {
    @Rule
    public ActivityScenarioRule<MapActivity> activityRule = new ActivityScenarioRule<>(MapActivity.class);

    @BeforeClass
    public static void setupAppModule() {
        TestAppModule testAppModule = new TestAppModule(App.getInstance().getApplicationContext());
        App.getInstance().setAppModule(testAppModule);
        Hawk.put("token", "It_is_token!");
        testAppModule.mapLocationsRepository.setGetLocationsForDateValue(Single.just(List.of(new Location(1.23, 4.56, 123456))));
    }

    @Test
    public void testAutoLogin() {
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }
}
