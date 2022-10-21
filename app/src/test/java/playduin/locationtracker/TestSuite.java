package playduin.locationtracker;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import playduin.locationtracker.bg.TestLocationServiceModel;
import playduin.locationtracker.models.auth.TestAuthRepo;
import playduin.locationtracker.models.locations.map.TestMapLocationsRepository;
import playduin.locationtracker.models.locations.tracker.TestTrackerLocationsRepository;
import playduin.locationtracker.ui.login.*;
import playduin.locationtracker.ui.map.*;
import playduin.locationtracker.ui.registration.*;
import playduin.locationtracker.ui.splash.*;
import playduin.locationtracker.ui.tracker.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        //bg
        TestLocationServiceModel.class,

        //models
        TestAuthRepo.class,
        TestMapLocationsRepository.class,
        TestTrackerLocationsRepository.class,

        //ui
        TestLoginScreenAction.class,
        TestLoginScreenState.class,
        TestLoginViewModel.class,

        TestMapScreenAction.class,
        TestMapScreenState.class,
        TestMapViewModel.class,

        TestRegistrationScreenAction.class,
        TestRegistrationScreenState.class,
        TestRegistrationViewModel.class,

        TestSplashScreenAction.class,
        TestSplashScreenState.class,
        TestSplashViewModel.class,

        TestTrackerScreenAction.class,
        TestTrackerScreenState.class,
        TestTrackerViewModel.class,
})

public class TestSuite {
}
