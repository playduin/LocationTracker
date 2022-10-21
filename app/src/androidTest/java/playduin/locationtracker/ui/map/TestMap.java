package playduin.locationtracker.ui.map;

import android.widget.DatePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import com.orhanobut.hawk.Hawk;

import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import playduin.locationtracker.App;
import playduin.locationtracker.R;
import playduin.locationtracker.models.TestAppModule;
import playduin.locationtracker.models.locations.Location;
import playduin.locationtracker.ui.MapActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.fail;
import static playduin.locationtracker.ui.TestConstants.TEST_TIME;

@RunWith(AndroidJUnit4.class)
public class TestMap {
    @Rule
    public ActivityScenarioRule<MapActivity> activityRule = new ActivityScenarioRule<>(MapActivity.class);

    private static TestAppModule testAppModule;

    @BeforeClass
    public static void setupAppModule() {
        testAppModule = new TestAppModule(App.getInstance().getApplicationContext());
        App.getInstance().setAppModule(testAppModule);
        Hawk.put("token", "It_is_token!");
        testAppModule.mapLocationsRepository.setGetLocationsForDateValue(Single.just(List.of(new Location(1.23, 4.56, 123456), new Location(1.2345, 4.5678, 123457), new Location(3.21, 6.54, 123458))));
    }

    @Test
    public void test() {
        testMap();
        testLogout();
    }

    private void testMap() {
        onView(withId(R.id.map)).check(matches(isDisplayed()));

        UiDevice device = UiDevice.getInstance(getInstrumentation());
        List<Location> locations = List.of(new Location(1.23, 4.56, 123456), new Location(1.2345, 4.5678, 123457), new Location(3.21, 6.54, 123458));
        List<Location> locations2 = List.of(new Location(1.23, 4.56, 123456), new Location(1.2345, 4.5678, 123457), new Location(3.21, 6.54, 123458), new Location(3.33, 0.0, 123459), new Location(0.0, 9.99, 123460));

        //test start
        for (Location location : locations) {
            UiObject marker = device.findObject(new UiSelector().descriptionContains(getDate(location.time) + " " + getTime(location.time)));
            try {
                marker.click();
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
                fail();
            }
            try {
                Thread.sleep(TEST_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //test select date
        testAppModule.mapLocationsRepository.setGetLocationsForDateValue(Single.just(List.of()));
        selectDate(2021, 10, 21);
        UiObject marker2 = device.findObject(new UiSelector().descriptionStartsWith(getDate(locations.get(0).time)));
        try {
            marker2.click();
            fail();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        //test sync
        testAppModule.mapLocationsRepository.setGetLocationsForDateValue(Single.just(locations2));
        onView(withId(R.id.sync_btn)).perform(click());
        for (Location location : locations2) {
            UiObject marker = device.findObject(new UiSelector().descriptionContains(getDate(location.time) + " " + getTime(location.time)));
            try {
                marker.click();
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
                fail();
            }
            try {
                Thread.sleep(TEST_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void testLogout() {
        testAppModule.authNetwork.setSignOutValue(Completable.complete());
        onView(withId(R.id.map)).check(matches(isDisplayed()));
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Sign out")).perform(click());
        onView(withId(R.id.login_fragment)).check(matches(isDisplayed()));
    }

    private static String getDate(long millis) {
        final DateFormat formatter = SimpleDateFormat.getDateInstance();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return formatter.format(calendar.getTime());
    }

    private static String getTime(long millis) {
        final DateFormat formatter = SimpleDateFormat.getTimeInstance();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return formatter.format(calendar.getTime());
    }

    private static void selectDate(int year, int month, int day) {
        onView(withId(R.id.date_select_btn)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, month, day));
        onView(withText("OK")).perform(click());
    }
}
