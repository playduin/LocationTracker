package playduin.locationtracker.ui.splash;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class TestSplashScreenAction {
    @Test
    public void testCreateSetLoginFragmentAction() {
        SplashContract.View splashContractView = mock(SplashContract.View.class);
        SplashScreenAction splashScreenAction = SplashScreenAction.createSetLoginFragmentAction();
        splashScreenAction.visit(splashContractView);

        verify(splashContractView).setLoginFragment();

        verifyNoMoreInteractions(splashContractView);
    }

    @Test
    public void testCreateSetTrackerFragmentAction() {
        SplashContract.View splashContractView = mock(SplashContract.View.class);
        SplashScreenAction splashScreenAction = SplashScreenAction.createSetTrackerFragmentAction();
        splashScreenAction.visit(splashContractView);

        verify(splashContractView).setTrackerFragment();

        verifyNoMoreInteractions(splashContractView);
    }

    @Test
    public void testCreateSetMapFragmentAction() {
        SplashContract.View splashContractView = mock(SplashContract.View.class);
        SplashScreenAction splashScreenAction = SplashScreenAction.createSetMapFragmentAction();
        splashScreenAction.visit(splashContractView);

        verify(splashContractView).setMapFragment();

        verifyNoMoreInteractions(splashContractView);
    }
}
