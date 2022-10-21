package playduin.locationtracker.ui.tracker;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.orhanobut.hawk.Hawk;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import playduin.locationtracker.App;
import playduin.locationtracker.R;
import playduin.locationtracker.models.TestAppModule;
import playduin.locationtracker.models.locations.Location;
import playduin.locationtracker.ui.TrackerActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static playduin.locationtracker.ui.TestConstants.TEST_TIME;

@RunWith(AndroidJUnit4.class)
public class TestTracker {
    @Rule
    public ActivityScenarioRule<TrackerActivity> activityRule = new ActivityScenarioRule<>(TrackerActivity.class);

    private static TestAppModule testAppModule;

    @BeforeClass
    public static void setupAppModule() {
        testAppModule = new TestAppModule(App.getInstance().getApplicationContext());
        App.getInstance().setAppModule(testAppModule);
        Hawk.put("token", "It_is_token!");
    }

    @Test
    public void test() {
        testTrackerLifecycle();
        testLogout();
    }

    private void testTrackerLifecycle() {
        final BehaviorSubject<Boolean> locationAvailabilityObservable = BehaviorSubject.createDefault(false);
        final BehaviorSubject<Location> locationObservable = BehaviorSubject.create();

        onView(withId(R.id.tracker)).check(matches(isDisplayed()));

        testAppModule.locationModel.setRequestLocationAvailabilityValue(locationAvailabilityObservable.observeOn(AndroidSchedulers.mainThread()));
        testAppModule.locationModel.setRequestLocationValue(locationObservable);

        //start
        onView(withId(R.id.status_text)).check(matches(withText("Tracker is not started.")));
        onView(withId(R.id.toggle_tracker)).check(matches(withText("Start"))).perform(click()).check(matches(withText("Stop")));
        onView(withId(R.id.status_text)).check(matches(withText("Tracker is started.")));

        //test locationAvailability
        locationAvailabilityObservable.onNext(true);
        onView(withId(R.id.gps_state_text)).check(matches(withText("GPS is ON")));

        locationAvailabilityObservable.onNext(false);
        onView(withId(R.id.gps_state_text)).check(matches(withText("GPS is OFF")));

        //test locations
        testAppModule.locationsNetwork.setSendLocationValue(Completable.complete());
        locationAvailabilityObservable.onNext(true);
        for (int i = 1; i < 4; ++i) {
            locationObservable.onNext(new Location(1.23, 4.56, 123456));
            try {
                Thread.sleep(TEST_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            onView(withId(R.id.locations_count_text)).check(matches(withText("Sent " + i + " locations")));
        }

        //test locations without send
        testAppModule.locationsNetwork.setSendLocationValue(Completable.error(new Error()));
        locationAvailabilityObservable.onNext(true);
        for (int i = 1; i < 4; ++i) {
            locationObservable.onNext(new Location(1.23, 4.56, 123456));
            try {
                Thread.sleep(TEST_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            onView(withId(R.id.locations_count_text)).check(matches(withText("Sent 3 locations")));
        }

        //stop
        onView(withId(R.id.status_text)).check(matches(withText("Tracker is started.")));
        onView(withId(R.id.toggle_tracker)).check(matches(withText("Stop"))).perform(click()).check(matches(withText("Start")));
        onView(withId(R.id.status_text)).check(matches(withText("Tracker is not started.")));

        //last check
        onView(withId(R.id.tracker)).check(matches(isDisplayed()));
    }

    private void testLogout() {
        testAppModule.authNetwork.setSignOutValue(Completable.complete());
        onView(withId(R.id.tracker)).check(matches(isDisplayed()));
        onView(withId(R.id.logout)).perform(click());
        onView(withText("Destroy locations")).perform(click());
        onView(withId(R.id.login_fragment)).check(matches(isDisplayed()));
    }
}
