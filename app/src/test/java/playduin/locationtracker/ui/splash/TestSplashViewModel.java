package playduin.locationtracker.ui.splash;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import playduin.locationtracker.models.auth.AuthRepo;
import playduin.locationtracker.ui.Launcher;
import playduin.locationtracker.ui.RxImmediateSchedulerRule;
import playduin.locationtracker.ui.mvi.ScreenAction;
import playduin.locationtracker.ui.mvi.ScreenState;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
public class TestSplashViewModel {
    @Rule
    public RxImmediateSchedulerRule testSchedulerRule = new RxImmediateSchedulerRule();
    @Rule
    public final InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private AuthRepo authRepo;

    @Before
    public void testSetup() {
        authRepo = mock(AuthRepo.class);
    }

    @Test
    public void testOnAnyWithSetTrackerFragment() {
        when(authRepo.hasToken()).thenReturn(true);

        SplashViewModel splashViewModel = new SplashViewModel(authRepo, Launcher.TRACKER);

        Observer<ScreenState<SplashContract.View>> screenStateObserver = (Observer<ScreenState<SplashContract.View>>) mock(Observer.class);
        splashViewModel.getStateObservable().observeForever(screenStateObserver);

        Observer<ScreenAction<SplashContract.View>> screenActionObserver = (Observer<ScreenAction<SplashContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenAction<SplashContract.View>> actionCaptor = ArgumentCaptor.forClass(ScreenAction.class);
        splashViewModel.getActionObservable().observeForever(screenActionObserver);

        splashViewModel.onAny(null, Lifecycle.Event.ON_START);

        verify(authRepo, times(1)).hasToken();

        verify(screenActionObserver, times(1)).onChanged(actionCaptor.capture());
        assertEquals(actionCaptor.getValue(), SplashScreenAction.createSetTrackerFragmentAction());

        verifyNoMoreInteractions(screenStateObserver);
        verifyNoMoreInteractions(screenActionObserver);

        splashViewModel.getStateObservable().removeObserver(screenStateObserver);
        splashViewModel.getActionObservable().removeObserver(screenActionObserver);

        verifyNoMoreInteractions(authRepo);
    }

    @Test
    public void testOnAnyWithSetMapFragment() {
        when(authRepo.hasToken()).thenReturn(true);

        SplashViewModel splashViewModel = new SplashViewModel(authRepo, Launcher.MAP);

        Observer<ScreenState<SplashContract.View>> screenStateObserver = (Observer<ScreenState<SplashContract.View>>) mock(Observer.class);
        splashViewModel.getStateObservable().observeForever(screenStateObserver);

        Observer<ScreenAction<SplashContract.View>> screenActionObserver = (Observer<ScreenAction<SplashContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenAction<SplashContract.View>> actionCaptor = ArgumentCaptor.forClass(ScreenAction.class);
        splashViewModel.getActionObservable().observeForever(screenActionObserver);

        splashViewModel.onAny(null, Lifecycle.Event.ON_START);

        verify(authRepo, times(1)).hasToken();

        verify(screenActionObserver, times(1)).onChanged(actionCaptor.capture());
        assertEquals(actionCaptor.getValue(), SplashScreenAction.createSetMapFragmentAction());

        verifyNoMoreInteractions(screenStateObserver);
        verifyNoMoreInteractions(screenActionObserver);

        splashViewModel.getStateObservable().removeObserver(screenStateObserver);
        splashViewModel.getActionObservable().removeObserver(screenActionObserver);

        verifyNoMoreInteractions(authRepo);
    }

    @Test
    public void testOnAnyWithSetLoginFragment() {
        when(authRepo.hasToken()).thenReturn(false);

        SplashViewModel splashViewModel = new SplashViewModel(authRepo, Launcher.TRACKER);

        Observer<ScreenState<SplashContract.View>> screenStateObserver = (Observer<ScreenState<SplashContract.View>>) mock(Observer.class);
        splashViewModel.getStateObservable().observeForever(screenStateObserver);

        Observer<ScreenAction<SplashContract.View>> screenActionObserver = (Observer<ScreenAction<SplashContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenAction<SplashContract.View>> actionCaptor = ArgumentCaptor.forClass(ScreenAction.class);
        splashViewModel.getActionObservable().observeForever(screenActionObserver);

        splashViewModel.onAny(null, Lifecycle.Event.ON_START);

        verify(authRepo, times(1)).hasToken();

        verify(screenActionObserver, times(1)).onChanged(actionCaptor.capture());
        assertEquals(actionCaptor.getValue(), SplashScreenAction.createSetLoginFragmentAction());

        verifyNoMoreInteractions(screenStateObserver);
        verifyNoMoreInteractions(screenActionObserver);

        splashViewModel.getStateObservable().removeObserver(screenStateObserver);
        splashViewModel.getActionObservable().removeObserver(screenActionObserver);

        verifyNoMoreInteractions(authRepo);
    }
}
