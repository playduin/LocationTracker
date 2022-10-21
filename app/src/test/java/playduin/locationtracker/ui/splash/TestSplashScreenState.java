package playduin.locationtracker.ui.splash;

import org.junit.Test;

import static org.mockito.Mockito.*;

public class TestSplashScreenState {
    @Test
    public void testEmpty() {
        SplashContract.View splashContractView = mock(SplashContract.View.class);
        SplashScreenState splashScreenState = new SplashScreenState();
        splashScreenState.visit(splashContractView);
        
        verifyNoMoreInteractions(splashContractView);
    }
}
