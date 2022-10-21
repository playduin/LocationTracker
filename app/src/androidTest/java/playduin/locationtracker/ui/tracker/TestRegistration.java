package playduin.locationtracker.ui.tracker;

import android.view.View;
import android.widget.EditText;

import androidx.test.espresso.Root;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.orhanobut.hawk.Hawk;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.rxjava3.core.Single;
import playduin.locationtracker.App;
import playduin.locationtracker.R;
import playduin.locationtracker.helpers.ToastMatcher;
import playduin.locationtracker.models.TestAppModule;
import playduin.locationtracker.ui.TrackerActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static playduin.locationtracker.ui.TestConstants.TEST_TIME;

@RunWith(AndroidJUnit4.class)
public class TestRegistration {
    @Rule
    public ActivityScenarioRule<TrackerActivity> activityRule = new ActivityScenarioRule<>(TrackerActivity.class);

    private static TestAppModule testAppModule;

    @BeforeClass
    public static void setupAppModule() {
        testAppModule = new TestAppModule(App.getInstance().getApplicationContext());
        App.getInstance().setAppModule(testAppModule);
        Hawk.delete("token");
    }

    @Test
    public void test() {
        testWrongTexts();
        testNetworkError();
        testMailError();
        testRegistration();
    }

    private void testWrongTexts() {
        onView(withId(R.id.login_fragment)).check(matches(isDisplayed()));
        onView(withId(R.id.register)).perform(click());
        onView(withId(R.id.registration_fragment)).check(matches(isDisplayed()));

        onView(withId(R.id.username)).perform(clearText()).perform(typeText("test")).check(matches(hasErrorText("Not a valid username")));
        onView(withId(R.id.username)).perform(clearText()).perform(typeText("test@127.0.0.1")).check(matches(hasNoErrorText()));

        onView(withId(R.id.password)).perform(clearText()).perform(typeText("123")).check(matches(hasErrorText("Password must be >5 characters")));
        onView(withId(R.id.password)).perform(clearText()).perform(typeText("123456")).check(matches(hasNoErrorText()));
    }

    private void testNetworkError() {
        onView(withId(R.id.registration_fragment)).check(matches(isDisplayed()));

        testAppModule.authNetwork.setCreateAccountValue(Single.error(new Error("com.google.firebase.FirebaseNetworkException")));

        onView(withId(R.id.username)).perform(clearText()).perform(typeText("test@127.0.0.1")).check(matches(hasNoErrorText()));
        onView(withId(R.id.password)).perform(clearText()).perform(typeText("123456")).check(matches(hasNoErrorText()));
        onView(withId(R.id.register)).perform(click());

        //TODO: check toast
        //isToastMessageDisplayed("Network error");
        //activityRule.getScenario().onActivity(activity -> onView(withText("Network error")).inRoot(withDecorView(not(activity.getWindow().getDecorView()))).check(matches(isDisplayed())));

        onView(withId(R.id.registration_fragment)).check(matches(isDisplayed()));
    }

    private void testMailError() {
        onView(withId(R.id.registration_fragment)).check(matches(isDisplayed()));

        testAppModule.authNetwork.setCreateAccountValue(Single.error(new Error("com.google.firebase.auth.FirebaseAuthUserCollisionException")));

        onView(withId(R.id.username)).perform(clearText()).perform(typeText("test@127.0.0.1")).check(matches(hasNoErrorText()));
        onView(withId(R.id.password)).perform(clearText()).perform(typeText("123456")).check(matches(hasNoErrorText()));
        onView(withId(R.id.register)).perform(click());

        //TODO: check toast

        onView(withId(R.id.registration_fragment)).check(matches(isDisplayed()));
    }

    private void testRegistration() {
        onView(withId(R.id.registration_fragment)).check(matches(isDisplayed()));

        testAppModule.authNetwork.setCreateAccountValue(Single.just("It_is_token!"));

        onView(withId(R.id.username)).perform(clearText()).perform(typeText("test@127.0.0.1")).check(matches(hasNoErrorText()));
        onView(withId(R.id.password)).perform(clearText()).perform(typeText("123456")).check(matches(hasNoErrorText()));
        onView(withId(R.id.register)).perform(click());

        try {
            Thread.sleep(TEST_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.tracker)).check(matches(isDisplayed()));
    }

    private static void isToastMessageDisplayed(String text) {
        onView(withText(text)).inRoot(isToast()).check(matches(isDisplayed()));
    }

    private static Matcher<Root> isToast() {
        return new ToastMatcher();
    }

    private static Matcher<View> hasNoErrorText() {
        return new BoundedMatcher<View, EditText>(EditText.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("has no error text: ");
            }

            @Override
            protected boolean matchesSafely(EditText view) {
                return view.getError() == null;
            }
        };
    }
}
