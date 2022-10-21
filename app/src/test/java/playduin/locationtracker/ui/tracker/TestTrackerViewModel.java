package playduin.locationtracker.ui.tracker;

import android.Manifest;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.core.content.PermissionChecker;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import playduin.locationtracker.R;
import playduin.locationtracker.models.auth.AuthRepo;
import playduin.locationtracker.models.cache.Cache;
import playduin.locationtracker.models.locations.tracker.TrackerLocationsRepository;
import playduin.locationtracker.ui.RxImmediateSchedulerRule;
import playduin.locationtracker.ui.logout.LogoutDialogState;
import playduin.locationtracker.ui.mvi.ScreenAction;
import playduin.locationtracker.ui.mvi.ScreenState;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
public class TestTrackerViewModel {
    @Rule
    public RxImmediateSchedulerRule testSchedulerRule = new RxImmediateSchedulerRule();
    @Rule
    public final InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private AuthRepo authRepo;
    private Cache cache;
    private TrackerLocationsRepository repo;
    private TrackerViewModel trackerViewModel;

    @Before
    public void testSetup() {
        authRepo = mock(AuthRepo.class);
        cache = mock(Cache.class);
        repo = mock(TrackerLocationsRepository.class);
        trackerViewModel = new TrackerViewModel(authRepo, cache, repo);
    }

    @Test
    public void testOnAnyWithTurnOnTracker() {
        BehaviorSubject<Boolean> trackerStateObservable = BehaviorSubject.create();

        when(cache.getTrackerState()).thenReturn(true);
        when(cache.getTrackerStateObservable()).thenReturn(trackerStateObservable);
        when(cache.getLocationAvailabilityObservable()).thenReturn(BehaviorSubject.create());
        when(cache.getLocationsCountObservable()).thenReturn(BehaviorSubject.create());

        Observer<ScreenState<TrackerContract.View>> screenStateObserver = (Observer<ScreenState<TrackerContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenState<TrackerContract.View>> stateCaptor = ArgumentCaptor.forClass(ScreenState.class);
        trackerViewModel.getStateObservable().observeForever(screenStateObserver);

        Observer<ScreenAction<TrackerContract.View>> screenActionObserver = (Observer<ScreenAction<TrackerContract.View>>) mock(Observer.class);
        trackerViewModel.getActionObservable().observeForever(screenActionObserver);

        trackerViewModel.onAny(null, Lifecycle.Event.ON_START);

        verifyOnAnyModels();

        trackerStateObservable.onNext(true);

        verify(screenStateObserver, times(2)).onChanged(stateCaptor.capture());
        List<ScreenState<TrackerContract.View>> screenStateList = stateCaptor.getAllValues();
        assertEquals(screenStateList.get(0), TrackerScreenState.createServiceRunningState(R.string.stop, R.string.tracker_is_started));
        assertEquals(screenStateList.get(1), TrackerScreenState.createServiceRunningState(R.string.stop, R.string.tracker_is_started));
        assertEquals(screenStateList.size(), 2);

        verifyNoMoreInteractions(screenStateObserver);
        verifyNoMoreInteractions(screenActionObserver);

        trackerViewModel.getStateObservable().removeObserver(screenStateObserver);
        trackerViewModel.getActionObservable().removeObserver(screenActionObserver);

        verifyNoMoreInteractionsInModels();
    }

    @Test
    public void testOnAnyWithTurnOffTracker() {
        BehaviorSubject<Boolean> trackerStateObservable = BehaviorSubject.create();

        when(cache.getTrackerState()).thenReturn(false);
        when(cache.getTrackerStateObservable()).thenReturn(trackerStateObservable);
        when(cache.getLocationAvailabilityObservable()).thenReturn(BehaviorSubject.create());
        when(cache.getLocationsCountObservable()).thenReturn(BehaviorSubject.create());

        Observer<ScreenState<TrackerContract.View>> screenStateObserver = (Observer<ScreenState<TrackerContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenState<TrackerContract.View>> stateCaptor = ArgumentCaptor.forClass(ScreenState.class);
        trackerViewModel.getStateObservable().observeForever(screenStateObserver);

        Observer<ScreenAction<TrackerContract.View>> screenActionObserver = (Observer<ScreenAction<TrackerContract.View>>) mock(Observer.class);
        trackerViewModel.getActionObservable().observeForever(screenActionObserver);

        trackerViewModel.onAny(null, Lifecycle.Event.ON_START);

        verifyOnAnyModels();

        trackerStateObservable.onNext(false);

        verify(screenStateObserver, times(2)).onChanged(stateCaptor.capture());
        List<ScreenState<TrackerContract.View>> screenStateList = stateCaptor.getAllValues();
        assertEquals(screenStateList.get(0), TrackerScreenState.createServiceStoppedState(R.string.start, R.string.tracker_is_not_started));
        assertEquals(screenStateList.get(1), TrackerScreenState.createServiceStoppedState(R.string.start, R.string.tracker_is_not_started));
        assertEquals(screenStateList.size(), 2);

        verifyNoMoreInteractions(screenStateObserver);
        verifyNoMoreInteractions(screenActionObserver);

        trackerViewModel.getStateObservable().removeObserver(screenStateObserver);
        trackerViewModel.getActionObservable().removeObserver(screenActionObserver);

        verifyNoMoreInteractionsInModels();
    }

    @Test
    public void testSetLocationAvailability() {
        BehaviorSubject<Boolean> locationAvailabilityObservable = BehaviorSubject.create();

        when(cache.getTrackerState()).thenReturn(false);
        when(cache.getTrackerStateObservable()).thenReturn(BehaviorSubject.create());
        when(cache.getLocationAvailabilityObservable()).thenReturn(locationAvailabilityObservable);
        when(cache.getLocationsCountObservable()).thenReturn(BehaviorSubject.create());

        Observer<ScreenState<TrackerContract.View>> screenStateObserver = (Observer<ScreenState<TrackerContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenState<TrackerContract.View>> stateCaptor = ArgumentCaptor.forClass(ScreenState.class);
        trackerViewModel.getStateObservable().observeForever(screenStateObserver);

        Observer<ScreenAction<TrackerContract.View>> screenActionObserver = (Observer<ScreenAction<TrackerContract.View>>) mock(Observer.class);
        trackerViewModel.getActionObservable().observeForever(screenActionObserver);

        trackerViewModel.onAny(null, Lifecycle.Event.ON_START);

        verifyOnAnyModels();

        locationAvailabilityObservable.onNext(false);
        locationAvailabilityObservable.onNext(true);

        verify(screenStateObserver, times(3)).onChanged(stateCaptor.capture());
        List<ScreenState<TrackerContract.View>> screenStateList = stateCaptor.getAllValues();
        assertEquals(screenStateList.get(0), TrackerScreenState.createServiceStoppedState(R.string.start, R.string.tracker_is_not_started));
        assertEquals(screenStateList.get(1), TrackerScreenState.createSetGPSState(false));
        assertEquals(screenStateList.get(2), TrackerScreenState.createSetGPSState(true));
        assertEquals(screenStateList.size(), 3);

        verifyNoMoreInteractions(screenStateObserver);
        verifyNoMoreInteractions(screenActionObserver);

        trackerViewModel.getStateObservable().removeObserver(screenStateObserver);
        trackerViewModel.getActionObservable().removeObserver(screenActionObserver);

        verifyNoMoreInteractionsInModels();
    }

    @Test
    public void testSetLocationsCount() {
        BehaviorSubject<Integer> locationsCountObservable = BehaviorSubject.create();

        when(cache.getTrackerState()).thenReturn(false);
        when(cache.getTrackerStateObservable()).thenReturn(BehaviorSubject.create());
        when(cache.getLocationAvailabilityObservable()).thenReturn(BehaviorSubject.create());
        when(cache.getLocationsCountObservable()).thenReturn(locationsCountObservable);

        Observer<ScreenState<TrackerContract.View>> screenStateObserver = (Observer<ScreenState<TrackerContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenState<TrackerContract.View>> stateCaptor = ArgumentCaptor.forClass(ScreenState.class);
        trackerViewModel.getStateObservable().observeForever(screenStateObserver);

        Observer<ScreenAction<TrackerContract.View>> screenActionObserver = (Observer<ScreenAction<TrackerContract.View>>) mock(Observer.class);
        trackerViewModel.getActionObservable().observeForever(screenActionObserver);

        trackerViewModel.onAny(null, Lifecycle.Event.ON_START);

        verifyOnAnyModels();

        locationsCountObservable.onNext(0);
        locationsCountObservable.onNext(123456789);

        verify(screenStateObserver, times(3)).onChanged(stateCaptor.capture());
        List<ScreenState<TrackerContract.View>> screenStateList = stateCaptor.getAllValues();
        assertEquals(screenStateList.get(0), TrackerScreenState.createServiceStoppedState(R.string.start, R.string.tracker_is_not_started));
        assertEquals(screenStateList.get(1), TrackerScreenState.createSetLocationsCountState(0));
        assertEquals(screenStateList.get(2), TrackerScreenState.createSetLocationsCountState(123456789));
        assertEquals(screenStateList.size(), 3);

        verifyNoMoreInteractions(screenStateObserver);
        verifyNoMoreInteractions(screenActionObserver);

        trackerViewModel.getStateObservable().removeObserver(screenStateObserver);
        trackerViewModel.getActionObservable().removeObserver(screenActionObserver);

        verifyNoMoreInteractionsInModels();
    }

    @Test
    public void testPermissionGrantSubscribeEmpty() {
        BehaviorSubject<String> permissionGrantObservable = BehaviorSubject.create();
        trackerViewModel.permissionGrantSubscribe(permissionGrantObservable.hide());

        Observer<ScreenState<TrackerContract.View>> screenStateObserver = (Observer<ScreenState<TrackerContract.View>>) mock(Observer.class);
        trackerViewModel.getStateObservable().observeForever(screenStateObserver);

        Observer<ScreenAction<TrackerContract.View>> screenActionObserver = (Observer<ScreenAction<TrackerContract.View>>) mock(Observer.class);
        trackerViewModel.getActionObservable().observeForever(screenActionObserver);

        verifyNoMoreInteractions(screenStateObserver);
        verifyNoMoreInteractions(screenActionObserver);

        trackerViewModel.getStateObservable().removeObserver(screenStateObserver);
        trackerViewModel.getActionObservable().removeObserver(screenActionObserver);

        verifyNoMoreInteractionsInModels();
    }

    @Test
    public void testPermissionGrantSubscribe() {
        BehaviorSubject<String> permissionGrantObservable = BehaviorSubject.create();
        trackerViewModel.permissionGrantSubscribe(permissionGrantObservable.hide());

        Observer<ScreenState<TrackerContract.View>> screenStateObserver = (Observer<ScreenState<TrackerContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenState<TrackerContract.View>> stateCaptor = ArgumentCaptor.forClass(ScreenState.class);
        trackerViewModel.getStateObservable().observeForever(screenStateObserver);

        Observer<ScreenAction<TrackerContract.View>> screenActionObserver = (Observer<ScreenAction<TrackerContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenAction<TrackerContract.View>> actionCaptor = ArgumentCaptor.forClass(ScreenAction.class);
        trackerViewModel.getActionObservable().observeForever(screenActionObserver);

        permissionGrantObservable.onNext(Manifest.permission.ACCESS_COARSE_LOCATION);

        verify(screenStateObserver, times(1)).onChanged(stateCaptor.capture());
        assertEquals(stateCaptor.getValue(), TrackerScreenState.createServiceRunningState(R.string.stop, R.string.tracker_is_started));

        verify(screenActionObserver, times(1)).onChanged(actionCaptor.capture());
        assertEquals(actionCaptor.getValue(), TrackerScreenAction.createStartTrackerAction());

        verifyNoMoreInteractions(screenStateObserver);
        verifyNoMoreInteractions(screenActionObserver);

        trackerViewModel.getStateObservable().removeObserver(screenStateObserver);
        trackerViewModel.getActionObservable().removeObserver(screenActionObserver);

        verifyNoMoreInteractionsInModels();
    }

    @Test
    public void testStartTrackerWithGrantedPermission() {
        when(cache.getTrackerState()).thenReturn(false);

        Observer<ScreenState<TrackerContract.View>> screenStateObserver = (Observer<ScreenState<TrackerContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenState<TrackerContract.View>> stateCaptor = ArgumentCaptor.forClass(ScreenState.class);
        trackerViewModel.getStateObservable().observeForever(screenStateObserver);

        Observer<ScreenAction<TrackerContract.View>> screenActionObserver = (Observer<ScreenAction<TrackerContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenAction<TrackerContract.View>> actionCaptor = ArgumentCaptor.forClass(ScreenAction.class);
        trackerViewModel.getActionObservable().observeForever(screenActionObserver);

        trackerViewModel.toggleTracker(PermissionChecker.PERMISSION_GRANTED);

        verify(cache, times(1)).getTrackerState();

        verify(screenStateObserver, times(1)).onChanged(stateCaptor.capture());
        assertEquals(stateCaptor.getValue(), TrackerScreenState.createServiceRunningState(R.string.stop, R.string.tracker_is_started));

        verify(screenActionObserver, times(1)).onChanged(actionCaptor.capture());
        assertEquals(actionCaptor.getValue(), TrackerScreenAction.createStartTrackerAction());

        verifyNoMoreInteractions(screenStateObserver);
        verifyNoMoreInteractions(screenActionObserver);

        trackerViewModel.getStateObservable().removeObserver(screenStateObserver);
        trackerViewModel.getActionObservable().removeObserver(screenActionObserver);

        verifyNoMoreInteractionsInModels();
    }

    @Test
    public void testStartTrackerWithDeniedPermission() {
        when(cache.getTrackerState()).thenReturn(false);

        Observer<ScreenState<TrackerContract.View>> screenStateObserver = (Observer<ScreenState<TrackerContract.View>>) mock(Observer.class);
        trackerViewModel.getStateObservable().observeForever(screenStateObserver);

        Observer<ScreenAction<TrackerContract.View>> screenActionObserver = (Observer<ScreenAction<TrackerContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenAction<TrackerContract.View>> actionCaptor = ArgumentCaptor.forClass(ScreenAction.class);
        trackerViewModel.getActionObservable().observeForever(screenActionObserver);

        trackerViewModel.toggleTracker(PermissionChecker.PERMISSION_DENIED);

        verify(cache, times(1)).getTrackerState();

        verify(screenActionObserver, times(1)).onChanged(actionCaptor.capture());
        assertEquals(actionCaptor.getValue(), TrackerScreenAction.createRequestPermissionAction());

        verifyNoMoreInteractions(screenStateObserver);
        verifyNoMoreInteractions(screenActionObserver);

        trackerViewModel.getStateObservable().removeObserver(screenStateObserver);
        trackerViewModel.getActionObservable().removeObserver(screenActionObserver);

        verifyNoMoreInteractionsInModels();
    }

    @Test
    public void testStopTracker() {
        when(cache.getTrackerState()).thenReturn(true);

        Observer<ScreenState<TrackerContract.View>> screenStateObserver = (Observer<ScreenState<TrackerContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenState<TrackerContract.View>> stateCaptor = ArgumentCaptor.forClass(ScreenState.class);
        trackerViewModel.getStateObservable().observeForever(screenStateObserver);

        Observer<ScreenAction<TrackerContract.View>> screenActionObserver = (Observer<ScreenAction<TrackerContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenAction<TrackerContract.View>> actionCaptor = ArgumentCaptor.forClass(ScreenAction.class);
        trackerViewModel.getActionObservable().observeForever(screenActionObserver);

        trackerViewModel.toggleTracker(PermissionChecker.PERMISSION_GRANTED);

        verify(cache, times(1)).getTrackerState();

        verify(screenStateObserver, times(1)).onChanged(stateCaptor.capture());
        assertEquals(stateCaptor.getValue(), TrackerScreenState.createServiceStoppedState(R.string.start, R.string.tracker_is_not_started));

        verify(screenActionObserver, times(1)).onChanged(actionCaptor.capture());
        assertEquals(actionCaptor.getValue(), TrackerScreenAction.createStopTrackerAction());

        verifyNoMoreInteractions(screenStateObserver);
        verifyNoMoreInteractions(screenActionObserver);

        trackerViewModel.getStateObservable().removeObserver(screenStateObserver);
        trackerViewModel.getActionObservable().removeObserver(screenActionObserver);

        verifyNoMoreInteractionsInModels();
    }

    @Test
    public void testLogout() {
        when(cache.getTrackerState()).thenReturn(true);
        when(repo.syncRemote()).thenReturn(Completable.complete());
        when(authRepo.signOut()).thenReturn(Completable.complete());

        Observer<ScreenState<TrackerContract.View>> screenStateObserver = (Observer<ScreenState<TrackerContract.View>>) mock(Observer.class);
        trackerViewModel.getStateObservable().observeForever(screenStateObserver);

        Observer<ScreenAction<TrackerContract.View>> screenActionObserver = (Observer<ScreenAction<TrackerContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenAction<TrackerContract.View>> actionCaptor = ArgumentCaptor.forClass(ScreenAction.class);
        trackerViewModel.getActionObservable().observeForever(screenActionObserver);

        trackerViewModel.logout();

        verify(cache, times(1)).getTrackerState();
        verify(repo, times(1)).syncRemote();
        verify(authRepo, times(1)).signOut();

        verify(screenActionObserver, times(2)).onChanged(actionCaptor.capture());
        List<ScreenAction<TrackerContract.View>> screenActionList = actionCaptor.getAllValues();
        assertEquals(screenActionList.get(0), TrackerScreenAction.createStopTrackerAction());
        assertEquals(screenActionList.get(1), TrackerScreenAction.createShowLoginFragmentAction());
        assertEquals(screenActionList.size(), 2);

        verifyNoMoreInteractions(screenStateObserver);
        verifyNoMoreInteractions(screenActionObserver);

        trackerViewModel.getStateObservable().removeObserver(screenStateObserver);
        trackerViewModel.getActionObservable().removeObserver(screenActionObserver);

        verifyNoMoreInteractionsInModels();
    }

    @Test
    public void testLogoutWithWaitLocations() {
        when(cache.getTrackerState()).thenReturn(true);
        when(cache.getLogoutDialogObservable()).thenReturn(Observable.just(LogoutDialogState.WAIT_CONNECTION_STATE));
        when(repo.syncRemote()).thenReturn(Completable.error(new Error()));
        when(authRepo.signOut()).thenReturn(Completable.complete());

        Observer<ScreenState<TrackerContract.View>> screenStateObserver = (Observer<ScreenState<TrackerContract.View>>) mock(Observer.class);
        trackerViewModel.getStateObservable().observeForever(screenStateObserver);

        Observer<ScreenAction<TrackerContract.View>> screenActionObserver = (Observer<ScreenAction<TrackerContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenAction<TrackerContract.View>> actionCaptor = ArgumentCaptor.forClass(ScreenAction.class);
        trackerViewModel.getActionObservable().observeForever(screenActionObserver);

        trackerViewModel.logout();

        verify(cache, times(1)).getTrackerState();
        verify(cache, times(1)).getLogoutDialogObservable();
        verify(repo, times(1)).syncRemote();

        verify(screenActionObserver, times(2)).onChanged(actionCaptor.capture());
        List<ScreenAction<TrackerContract.View>> screenActionList = actionCaptor.getAllValues();
        assertEquals(screenActionList.get(0), TrackerScreenAction.createStopTrackerAction());
        assertEquals(screenActionList.get(1), TrackerScreenAction.createShowLogoutAlertDialog());
        assertEquals(screenActionList.size(), 2);

        verifyNoMoreInteractions(screenStateObserver);
        verifyNoMoreInteractions(screenActionObserver);

        trackerViewModel.getStateObservable().removeObserver(screenStateObserver);
        trackerViewModel.getActionObservable().removeObserver(screenActionObserver);

        verifyNoMoreInteractionsInModels();
    }

    @Test
    public void testLogoutWithClearLocations() {
        when(cache.getTrackerState()).thenReturn(true);
        when(cache.getLogoutDialogObservable()).thenReturn(Observable.just(LogoutDialogState.CLEAR_LOCATIONS_STATE));
        when(repo.syncRemote()).thenReturn(Completable.error(new Error()));
        when(authRepo.signOut()).thenReturn(Completable.complete());

        Observer<ScreenState<TrackerContract.View>> screenStateObserver = (Observer<ScreenState<TrackerContract.View>>) mock(Observer.class);
        trackerViewModel.getStateObservable().observeForever(screenStateObserver);

        Observer<ScreenAction<TrackerContract.View>> screenActionObserver = (Observer<ScreenAction<TrackerContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenAction<TrackerContract.View>> actionCaptor = ArgumentCaptor.forClass(ScreenAction.class);
        trackerViewModel.getActionObservable().observeForever(screenActionObserver);

        trackerViewModel.logout();

        verify(cache, times(1)).getTrackerState();
        verify(cache, times(1)).getLogoutDialogObservable();
        verify(repo, times(1)).syncRemote();
        verify(authRepo, times(1)).signOut();

        verify(screenActionObserver, times(3)).onChanged(actionCaptor.capture());
        List<ScreenAction<TrackerContract.View>> screenActionList = actionCaptor.getAllValues();
        assertEquals(screenActionList.get(0), TrackerScreenAction.createStopTrackerAction());
        assertEquals(screenActionList.get(1), TrackerScreenAction.createShowLogoutAlertDialog());
        assertEquals(screenActionList.get(2), TrackerScreenAction.createShowLoginFragmentAction());
        assertEquals(screenActionList.size(), 3);

        verifyNoMoreInteractions(screenStateObserver);
        verifyNoMoreInteractions(screenActionObserver);

        trackerViewModel.getStateObservable().removeObserver(screenStateObserver);
        trackerViewModel.getActionObservable().removeObserver(screenActionObserver);

        verifyNoMoreInteractionsInModels();
    }

    private void verifyOnAnyModels() {
        verify(cache, times(1)).getTrackerState();
        verify(cache, times(1)).getTrackerStateObservable();
        verify(cache, times(1)).getLocationAvailabilityObservable();
        verify(cache, times(1)).getLocationsCountObservable();
    }

    private void verifyNoMoreInteractionsInModels() {
        verifyNoMoreInteractions(cache);
        verifyNoMoreInteractions(repo);
        verifyNoMoreInteractions(authRepo);
    }
}
