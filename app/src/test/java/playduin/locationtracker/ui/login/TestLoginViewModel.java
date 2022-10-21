package playduin.locationtracker.ui.login;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import playduin.locationtracker.R;
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
public class TestLoginViewModel {
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
    public void testTrackerLoginSuccess() {
        when(authRepo.signIn("test", "123")).thenReturn(Single.just("It_is_token!!!"));

        LoginViewModel loginViewModel = new LoginViewModel(authRepo, Launcher.TRACKER);

        Observer<ScreenState<LoginContract.View>> screenStateObserver = (Observer<ScreenState<LoginContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenState<LoginContract.View>> stateCaptor = ArgumentCaptor.forClass(ScreenState.class);
        loginViewModel.getStateObservable().observeForever(screenStateObserver);

        Observer<ScreenAction<LoginContract.View>> screenActionObserver = (Observer<ScreenAction<LoginContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenAction<LoginContract.View>> actionCaptor = ArgumentCaptor.forClass(ScreenAction.class);
        loginViewModel.getActionObservable().observeForever(screenActionObserver);

        loginViewModel.login("test", "123");

        verify(authRepo, times(1)).signIn("test", "123");

        verify(screenStateObserver, times(2)).onChanged(stateCaptor.capture());
        List<ScreenState<LoginContract.View>> screenStateList = stateCaptor.getAllValues();
        assertEquals(screenStateList.get(0), LoginScreenState.createShowProgressBarState());
        assertEquals(screenStateList.get(1), LoginScreenState.createHideProgressBarState());
        assertEquals(screenStateList.size(), 2);

        verify(screenActionObserver, times(1)).onChanged(actionCaptor.capture());
        List<ScreenAction<LoginContract.View>> screenActionList = actionCaptor.getAllValues();
        assertEquals(screenActionList.get(0), LoginScreenAction.createSetTrackerFragmentAction());
        assertEquals(screenActionList.size(), 1);

        verifyNoMoreInteractions(screenStateObserver);
        verifyNoMoreInteractions(screenActionObserver);

        loginViewModel.getStateObservable().removeObserver(screenStateObserver);
        loginViewModel.getActionObservable().removeObserver(screenActionObserver);

        verifyNoMoreInteractions(authRepo);
    }

    @Test
    public void testMapLoginSuccess() {
        when(authRepo.signIn("test", "123")).thenReturn(Single.just("It_is_token!!!"));

        LoginViewModel loginViewModel = new LoginViewModel(authRepo, Launcher.MAP);

        Observer<ScreenState<LoginContract.View>> screenStateObserver = (Observer<ScreenState<LoginContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenState<LoginContract.View>> stateCaptor = ArgumentCaptor.forClass(ScreenState.class);
        loginViewModel.getStateObservable().observeForever(screenStateObserver);

        Observer<ScreenAction<LoginContract.View>> screenActionObserver = (Observer<ScreenAction<LoginContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenAction<LoginContract.View>> actionCaptor = ArgumentCaptor.forClass(ScreenAction.class);
        loginViewModel.getActionObservable().observeForever(screenActionObserver);

        loginViewModel.login("test", "123");

        verify(authRepo, times(1)).signIn("test", "123");

        verify(screenStateObserver, times(2)).onChanged(stateCaptor.capture());
        List<ScreenState<LoginContract.View>> screenStateList = stateCaptor.getAllValues();
        assertEquals(screenStateList.get(0), LoginScreenState.createShowProgressBarState());
        assertEquals(screenStateList.get(1), LoginScreenState.createHideProgressBarState());
        assertEquals(screenStateList.size(), 2);

        verify(screenActionObserver, times(1)).onChanged(actionCaptor.capture());
        List<ScreenAction<LoginContract.View>> screenActionList = actionCaptor.getAllValues();
        assertEquals(screenActionList.get(0), LoginScreenAction.createSetMapFragmentAction());
        assertEquals(screenActionList.size(), 1);

        verifyNoMoreInteractions(screenStateObserver);
        verifyNoMoreInteractions(screenActionObserver);

        loginViewModel.getStateObservable().removeObserver(screenStateObserver);
        loginViewModel.getActionObservable().removeObserver(screenActionObserver);

        verifyNoMoreInteractions(authRepo);
    }

    @Test
    public void testLoginError() {
        when(authRepo.signIn("test", "123")).thenReturn(Single.fromCallable(() -> {
            throw new Error("com.google.firebase.FirebaseNetworkException");
        }));

        LoginViewModel loginViewModel = new LoginViewModel(authRepo, Launcher.TRACKER);

        Observer<ScreenState<LoginContract.View>> screenStateObserver = (Observer<ScreenState<LoginContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenState<LoginContract.View>> stateCaptor = ArgumentCaptor.forClass(ScreenState.class);
        loginViewModel.getStateObservable().observeForever(screenStateObserver);

        Observer<ScreenAction<LoginContract.View>> screenActionObserver = (Observer<ScreenAction<LoginContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenAction<LoginContract.View>> actionCaptor = ArgumentCaptor.forClass(ScreenAction.class);
        loginViewModel.getActionObservable().observeForever(screenActionObserver);

        loginViewModel.login("test", "123");

        verify(authRepo, times(1)).signIn("test", "123");

        verify(screenStateObserver, times(2)).onChanged(stateCaptor.capture());
        List<ScreenState<LoginContract.View>> screenStateList = stateCaptor.getAllValues();
        assertEquals(screenStateList.get(0), LoginScreenState.createShowProgressBarState());
        assertEquals(screenStateList.get(1), LoginScreenState.createHideProgressBarState());
        assertEquals(screenStateList.size(), 2);

        verify(screenActionObserver, times(1)).onChanged(actionCaptor.capture());
        List<ScreenAction<LoginContract.View>> screenActionList = actionCaptor.getAllValues();
        assertEquals(screenActionList.get(0), LoginScreenAction.createShowToastAction(R.string.network_error));
        assertEquals(screenActionList.size(), 1);

        verifyNoMoreInteractions(screenStateObserver);
        verifyNoMoreInteractions(screenActionObserver);

        loginViewModel.getStateObservable().removeObserver(screenStateObserver);
        loginViewModel.getActionObservable().removeObserver(screenActionObserver);

        verifyNoMoreInteractions(authRepo);
    }

    @Test
    public void testOnRegistrationButtonClick() {
        LoginViewModel loginViewModel = new LoginViewModel(authRepo, Launcher.TRACKER);

        Observer<ScreenState<LoginContract.View>> screenStateObserver = (Observer<ScreenState<LoginContract.View>>) mock(Observer.class);
        loginViewModel.getStateObservable().observeForever(screenStateObserver);

        Observer<ScreenAction<LoginContract.View>> screenActionObserver = (Observer<ScreenAction<LoginContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenAction<LoginContract.View>> actionCaptor = ArgumentCaptor.forClass(ScreenAction.class);
        loginViewModel.getActionObservable().observeForever(screenActionObserver);

        loginViewModel.onRegistrationButtonClick();

        verify(screenActionObserver, times(1)).onChanged(actionCaptor.capture());
        List<ScreenAction<LoginContract.View>> screenActionList = actionCaptor.getAllValues();
        assertEquals(screenActionList.get(0), LoginScreenAction.createSetRegistrationFragmentAction());
        assertEquals(screenActionList.size(), 1);

        verifyNoMoreInteractions(screenStateObserver);
        verifyNoMoreInteractions(screenActionObserver);

        loginViewModel.getStateObservable().removeObserver(screenStateObserver);
        loginViewModel.getActionObservable().removeObserver(screenActionObserver);

        verifyNoMoreInteractions(authRepo);
    }

    @Test
    public void testLoginDataChanged() {
        LoginViewModel loginViewModel = new LoginViewModel(authRepo, Launcher.TRACKER);

        Observer<ScreenState<LoginContract.View>> screenStateObserver = (Observer<ScreenState<LoginContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenState<LoginContract.View>> stateCaptor = ArgumentCaptor.forClass(ScreenState.class);
        loginViewModel.getStateObservable().observeForever(screenStateObserver);

        Observer<ScreenAction<LoginContract.View>> screenActionObserver = (Observer<ScreenAction<LoginContract.View>>) mock(Observer.class);
        loginViewModel.getActionObservable().observeForever(screenActionObserver);

        loginViewModel.loginDataChanged(null, "123123");

        verify(screenStateObserver, times(1)).onChanged(stateCaptor.capture());
        List<ScreenState<LoginContract.View>> screenStateList = stateCaptor.getAllValues();
        assertEquals(screenStateList.get(0), LoginScreenState.createSetFormState(true, false));
        assertEquals(screenStateList.size(), 1);

        verifyNoMoreInteractions(screenStateObserver);
        verifyNoMoreInteractions(screenActionObserver);

        loginViewModel.getStateObservable().removeObserver(screenStateObserver);
        loginViewModel.getActionObservable().removeObserver(screenActionObserver);

        verifyNoMoreInteractions(authRepo);
    }
}
