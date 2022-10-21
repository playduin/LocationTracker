package playduin.locationtracker.ui.registration;

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
public class TestRegistrationViewModel {
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
    public void testTrackerRegistrationSuccess() {
        when(authRepo.createAccount("test", "123")).thenReturn(Single.just("It_is_token!!!"));

        RegistrationViewModel registrationViewModel = new RegistrationViewModel(authRepo, Launcher.TRACKER);

        Observer<ScreenState<RegistrationContract.View>> screenStateObserver = (Observer<ScreenState<RegistrationContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenState<RegistrationContract.View>> stateCaptor = ArgumentCaptor.forClass(ScreenState.class);
        registrationViewModel.getStateObservable().observeForever(screenStateObserver);

        Observer<ScreenAction<RegistrationContract.View>> screenActionObserver = (Observer<ScreenAction<RegistrationContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenAction<RegistrationContract.View>> actionCaptor = ArgumentCaptor.forClass(ScreenAction.class);
        registrationViewModel.getActionObservable().observeForever(screenActionObserver);

        registrationViewModel.register("test", "123");

        verify(authRepo, times(1)).createAccount("test", "123");

        verify(screenStateObserver, times(2)).onChanged(stateCaptor.capture());
        List<ScreenState<RegistrationContract.View>> screenStateList = stateCaptor.getAllValues();
        assertEquals(screenStateList.get(0), RegistrationScreenState.createShowProgressBarState());
        assertEquals(screenStateList.get(1), RegistrationScreenState.createHideProgressBarState());
        assertEquals(screenStateList.size(), 2);

        verify(screenActionObserver, times(1)).onChanged(actionCaptor.capture());
        List<ScreenAction<RegistrationContract.View>> screenActionList = actionCaptor.getAllValues();
        assertEquals(screenActionList.get(0), RegistrationScreenAction.createSetTrackerFragmentAction());
        assertEquals(screenActionList.size(), 1);

        verifyNoMoreInteractions(screenStateObserver);
        verifyNoMoreInteractions(screenActionObserver);

        registrationViewModel.getStateObservable().removeObserver(screenStateObserver);
        registrationViewModel.getActionObservable().removeObserver(screenActionObserver);

        verifyNoMoreInteractions(authRepo);
    }

    @Test
    public void testMapRegistrationSuccess() {
        when(authRepo.createAccount("test", "123")).thenReturn(Single.just("It_is_token!!!"));

        RegistrationViewModel registrationViewModel = new RegistrationViewModel(authRepo, Launcher.MAP);

        Observer<ScreenState<RegistrationContract.View>> screenStateObserver = (Observer<ScreenState<RegistrationContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenState<RegistrationContract.View>> stateCaptor = ArgumentCaptor.forClass(ScreenState.class);
        registrationViewModel.getStateObservable().observeForever(screenStateObserver);

        Observer<ScreenAction<RegistrationContract.View>> screenActionObserver = (Observer<ScreenAction<RegistrationContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenAction<RegistrationContract.View>> actionCaptor = ArgumentCaptor.forClass(ScreenAction.class);
        registrationViewModel.getActionObservable().observeForever(screenActionObserver);

        registrationViewModel.register("test", "123");

        verify(authRepo, times(1)).createAccount("test", "123");

        verify(screenStateObserver, times(2)).onChanged(stateCaptor.capture());
        List<ScreenState<RegistrationContract.View>> screenStateList = stateCaptor.getAllValues();
        assertEquals(screenStateList.get(0), RegistrationScreenState.createShowProgressBarState());
        assertEquals(screenStateList.get(1), RegistrationScreenState.createHideProgressBarState());
        assertEquals(screenStateList.size(), 2);

        verify(screenActionObserver, times(1)).onChanged(actionCaptor.capture());
        List<ScreenAction<RegistrationContract.View>> screenActionList = actionCaptor.getAllValues();
        assertEquals(screenActionList.get(0), RegistrationScreenAction.createSetMapFragmentAction());
        assertEquals(screenActionList.size(), 1);

        verifyNoMoreInteractions(screenStateObserver);
        verifyNoMoreInteractions(screenActionObserver);

        registrationViewModel.getStateObservable().removeObserver(screenStateObserver);
        registrationViewModel.getActionObservable().removeObserver(screenActionObserver);

        verifyNoMoreInteractions(authRepo);
    }

    @Test
    public void testRegistrationError() {
        when(authRepo.createAccount("test", "123")).thenReturn(Single.fromCallable(() -> {
            throw new Error("com.google.firebase.FirebaseNetworkException");
        }));

        RegistrationViewModel registrationViewModel = new RegistrationViewModel(authRepo, Launcher.TRACKER);

        Observer<ScreenState<RegistrationContract.View>> screenStateObserver = (Observer<ScreenState<RegistrationContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenState<RegistrationContract.View>> stateCaptor = ArgumentCaptor.forClass(ScreenState.class);
        registrationViewModel.getStateObservable().observeForever(screenStateObserver);

        Observer<ScreenAction<RegistrationContract.View>> screenActionObserver = (Observer<ScreenAction<RegistrationContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenAction<RegistrationContract.View>> actionCaptor = ArgumentCaptor.forClass(ScreenAction.class);
        registrationViewModel.getActionObservable().observeForever(screenActionObserver);

        registrationViewModel.register("test", "123");

        verify(authRepo, times(1)).createAccount("test", "123");

        verify(screenStateObserver, times(2)).onChanged(stateCaptor.capture());
        List<ScreenState<RegistrationContract.View>> screenStateList = stateCaptor.getAllValues();
        assertEquals(screenStateList.get(0), RegistrationScreenState.createShowProgressBarState());
        assertEquals(screenStateList.get(1), RegistrationScreenState.createHideProgressBarState());
        assertEquals(screenStateList.size(), 2);

        verify(screenActionObserver, times(1)).onChanged(actionCaptor.capture());
        List<ScreenAction<RegistrationContract.View>> screenActionList = actionCaptor.getAllValues();
        assertEquals(screenActionList.get(0), RegistrationScreenAction.createShowToastAction(R.string.network_error));
        assertEquals(screenActionList.size(), 1);

        verifyNoMoreInteractions(screenStateObserver);
        verifyNoMoreInteractions(screenActionObserver);

        registrationViewModel.getStateObservable().removeObserver(screenStateObserver);
        registrationViewModel.getActionObservable().removeObserver(screenActionObserver);

        verifyNoMoreInteractions(authRepo);
    }

    @Test
    public void testOnRegistrationButtonClick() {
        RegistrationViewModel registrationViewModel = new RegistrationViewModel(authRepo, Launcher.TRACKER);

        Observer<ScreenState<RegistrationContract.View>> screenStateObserver = (Observer<ScreenState<RegistrationContract.View>>) mock(Observer.class);
        registrationViewModel.getStateObservable().observeForever(screenStateObserver);

        Observer<ScreenAction<RegistrationContract.View>> screenActionObserver = (Observer<ScreenAction<RegistrationContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenAction<RegistrationContract.View>> actionCaptor = ArgumentCaptor.forClass(ScreenAction.class);
        registrationViewModel.getActionObservable().observeForever(screenActionObserver);

        registrationViewModel.onLoginButtonClick();

        verify(screenActionObserver, times(1)).onChanged(actionCaptor.capture());
        List<ScreenAction<RegistrationContract.View>> screenActionList = actionCaptor.getAllValues();
        assertEquals(screenActionList.get(0), RegistrationScreenAction.createSetLoginFragmentAction());
        assertEquals(screenActionList.size(), 1);

        verifyNoMoreInteractions(screenStateObserver);
        verifyNoMoreInteractions(screenActionObserver);

        registrationViewModel.getStateObservable().removeObserver(screenStateObserver);
        registrationViewModel.getActionObservable().removeObserver(screenActionObserver);

        verifyNoMoreInteractions(authRepo);
    }

    @Test
    public void testRegistrationDataChanged() {
        RegistrationViewModel registrationViewModel = new RegistrationViewModel(authRepo, Launcher.TRACKER);

        Observer<ScreenState<RegistrationContract.View>> screenStateObserver = (Observer<ScreenState<RegistrationContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenState<RegistrationContract.View>> stateCaptor = ArgumentCaptor.forClass(ScreenState.class);
        registrationViewModel.getStateObservable().observeForever(screenStateObserver);

        Observer<ScreenAction<RegistrationContract.View>> screenActionObserver = (Observer<ScreenAction<RegistrationContract.View>>) mock(Observer.class);
        registrationViewModel.getActionObservable().observeForever(screenActionObserver);

        registrationViewModel.loginDataChanged(null, "123123");

        verify(screenStateObserver, times(1)).onChanged(stateCaptor.capture());
        List<ScreenState<RegistrationContract.View>> screenStateList = stateCaptor.getAllValues();
        assertEquals(screenStateList.get(0), RegistrationScreenState.createSetFormState(true, false));
        assertEquals(screenStateList.size(), 1);

        verifyNoMoreInteractions(screenStateObserver);
        verifyNoMoreInteractions(screenActionObserver);

        registrationViewModel.getStateObservable().removeObserver(screenStateObserver);
        registrationViewModel.getActionObservable().removeObserver(screenActionObserver);

        verifyNoMoreInteractions(authRepo);
    }
}
