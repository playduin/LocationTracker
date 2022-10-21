package playduin.locationtracker;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        //tracker
        playduin.locationtracker.ui.tracker.TestSwitchLoginRegister.class,
        playduin.locationtracker.ui.tracker.TestAutoLogin.class,
        playduin.locationtracker.ui.tracker.TestLogin.class,
        playduin.locationtracker.ui.tracker.TestRegistration.class,
        playduin.locationtracker.ui.tracker.TestTracker.class,

        //map
        playduin.locationtracker.ui.map.TestSwitchLoginRegister.class,
        playduin.locationtracker.ui.map.TestAutoLogin.class,
        playduin.locationtracker.ui.map.TestLogin.class,
        playduin.locationtracker.ui.map.TestRegistration.class,
        playduin.locationtracker.ui.map.TestMap.class,
})

public class TestSuite {
}
