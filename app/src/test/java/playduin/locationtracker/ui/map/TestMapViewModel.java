package playduin.locationtracker.ui.map;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import playduin.locationtracker.models.auth.AuthRepo;
import playduin.locationtracker.models.cache.Cache;
import playduin.locationtracker.models.locations.Location;
import playduin.locationtracker.models.locations.Marker;
import playduin.locationtracker.models.locations.map.MapLocationsRepository;
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
public class TestMapViewModel {
    @Rule
    public RxImmediateSchedulerRule testSchedulerRule = new RxImmediateSchedulerRule();
    @Rule
    public final InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private AuthRepo authRepo;
    private MapLocationsRepository repo;
    private Cache cache;
    private MapViewModel mapViewModel;

    @Before
    public void testSetup() {
        authRepo = mock(AuthRepo.class);
        repo = mock(MapLocationsRepository.class);
        cache = mock(Cache.class);
        mapViewModel = new MapViewModel(authRepo, repo, cache);
    }

    @Test
    public void testCreateMapWithAllLocations() {
        List<Location> locations = new ArrayList<>();
        locations.add(new Location(1.23, 3.21, 123456));
        locations.add(new Location(0, 0, 123457));
        locations.add(new Location(3.21, 1.23, 123458));

        when(cache.getDatePickerObservable()).thenReturn(BehaviorSubject.create());
        when(repo.getLocationsForDate(MapLocationsRepository.ANY_DATE)).thenReturn(Single.just(locations));

        Observer<ScreenState<MapContract.View>> screenStateObserver = (Observer<ScreenState<MapContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenState<MapContract.View>> stateCaptor = ArgumentCaptor.forClass(ScreenState.class);
        mapViewModel.getStateObservable().observeForever(screenStateObserver);

        Observer<ScreenAction<MapContract.View>> screenActionObserver = (Observer<ScreenAction<MapContract.View>>) mock(Observer.class);
        mapViewModel.getActionObservable().observeForever(screenActionObserver);

        mapViewModel.onAny(null, Lifecycle.Event.ON_START);

        mapViewModel.createMarkers();

        verify(cache).getDatePickerObservable();
        verify(repo, times(2)).getLocationsForDate(MapLocationsRepository.ANY_DATE);

        verify(screenStateObserver, times(6)).onChanged(stateCaptor.capture());
        List<ScreenState<MapContract.View>> screenStateList = stateCaptor.getAllValues();
        assertEquals(screenStateList.get(0), MapScreenState.createClearMapState());
        assertEquals(screenStateList.get(1), MapScreenState.createAddMarkersState(getMarkers(locations)));
        assertEquals(screenStateList.get(2), MapScreenState.createMoveCameraState(new LatLng(3.21, 1.23)));
        assertEquals(screenStateList.get(3), MapScreenState.createClearMapState());
        assertEquals(screenStateList.get(4), MapScreenState.createAddMarkersState(getMarkers(locations)));
        assertEquals(screenStateList.get(5), MapScreenState.createMoveCameraState(new LatLng(3.21, 1.23)));
        assertEquals(screenStateList.size(), 6);

        verifyNoMoreInteractions(screenStateObserver);
        verifyNoMoreInteractions(screenActionObserver);

        mapViewModel.getStateObservable().removeObserver(screenStateObserver);
        mapViewModel.getActionObservable().removeObserver(screenActionObserver);

        verifyNoMoreInteractionsInModels();
    }

    @Test
    public void testCreateMapWithSomeLocations() {
        List<Location> locations = new ArrayList<>();
        locations.add(new Location(1.23, 3.21, 123456));
        locations.add(new Location(0, 0, 123457));
        locations.add(new Location(3.21, 1.23, 123458));

        List<Location> someLocations = new ArrayList<>();
        someLocations.add(new Location(0, 0, 123457));
        someLocations.add(new Location(3.21, 1.23, 123458));

        BehaviorSubject<Long> updateLocationsObservable = BehaviorSubject.create();

        when(cache.getDatePickerObservable()).thenReturn(updateLocationsObservable);
        when(repo.getLocationsForDate(MapLocationsRepository.ANY_DATE)).thenReturn(Single.just(locations));
        when(repo.getLocationsForDate(123457L)).thenReturn(Single.just(someLocations));

        Observer<ScreenState<MapContract.View>> screenStateObserver = (Observer<ScreenState<MapContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenState<MapContract.View>> stateCaptor = ArgumentCaptor.forClass(ScreenState.class);
        mapViewModel.getStateObservable().observeForever(screenStateObserver);

        Observer<ScreenAction<MapContract.View>> screenActionObserver = (Observer<ScreenAction<MapContract.View>>) mock(Observer.class);
        mapViewModel.getActionObservable().observeForever(screenActionObserver);

        mapViewModel.onAny(null, Lifecycle.Event.ON_START);

        updateLocationsObservable.onNext(123457L);

        verify(cache).getDatePickerObservable();
        verify(repo, times(1)).getLocationsForDate(MapLocationsRepository.ANY_DATE);
        verify(repo, times(1)).getLocationsForDate(123457L);

        verify(screenStateObserver, times(6)).onChanged(stateCaptor.capture());
        List<ScreenState<MapContract.View>> screenStateList = stateCaptor.getAllValues();
        assertEquals(screenStateList.get(0), MapScreenState.createClearMapState());
        assertEquals(screenStateList.get(1), MapScreenState.createAddMarkersState(getMarkers(locations)));
        assertEquals(screenStateList.get(2), MapScreenState.createMoveCameraState(new LatLng(3.21, 1.23)));
        assertEquals(screenStateList.get(3), MapScreenState.createClearMapState());
        assertEquals(screenStateList.get(4), MapScreenState.createAddMarkersState(getMarkers(someLocations)));
        assertEquals(screenStateList.get(5), MapScreenState.createMoveCameraState(new LatLng(3.21, 1.23)));
        assertEquals(screenStateList.size(), 6);

        verifyNoMoreInteractions(screenStateObserver);
        verifyNoMoreInteractions(screenActionObserver);

        mapViewModel.getStateObservable().removeObserver(screenStateObserver);
        mapViewModel.getActionObservable().removeObserver(screenActionObserver);

        verifyNoMoreInteractionsInModels();
    }

    @Test
    public void testLogout() {
        when(authRepo.signOut()).thenReturn(Completable.complete());

        Observer<ScreenState<MapContract.View>> screenStateObserver = (Observer<ScreenState<MapContract.View>>) mock(Observer.class);
        mapViewModel.getStateObservable().observeForever(screenStateObserver);

        Observer<ScreenAction<MapContract.View>> screenActionObserver = (Observer<ScreenAction<MapContract.View>>) mock(Observer.class);
        ArgumentCaptor<ScreenAction<MapContract.View>> actionCaptor = ArgumentCaptor.forClass(ScreenAction.class);
        mapViewModel.getActionObservable().observeForever(screenActionObserver);

        mapViewModel.logout();

        verify(authRepo).signOut();

        verify(screenActionObserver, times(1)).onChanged(actionCaptor.capture());
        List<ScreenAction<MapContract.View>> screenActionList = actionCaptor.getAllValues();
        assertEquals(screenActionList.get(0), MapScreenAction.createShowLoginFragmentAction());
        assertEquals(screenActionList.size(), 1);

        verifyNoMoreInteractions(screenStateObserver);
        verifyNoMoreInteractions(screenActionObserver);

        mapViewModel.getStateObservable().removeObserver(screenStateObserver);
        mapViewModel.getActionObservable().removeObserver(screenActionObserver);

        verifyNoMoreInteractionsInModels();
    }

    private void verifyNoMoreInteractionsInModels() {
        verifyNoMoreInteractions(cache);
        verifyNoMoreInteractions(repo);
        verifyNoMoreInteractions(authRepo);
    }

    private List<Marker> getMarkers(List<Location> locations) {
        final List<Marker> markers = new ArrayList<>();
        for (Location location : locations) {
            LatLng pos = new LatLng(location.latitude, location.longitude);
            markers.add(new Marker(pos, getDate(location.time) + " " + getTime(location.time)));
        }
        return markers;
    }

    private static String getDate(long millis) {
        final DateFormat formatter = SimpleDateFormat.getDateInstance();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return formatter.format(calendar.getTime());
    }

    private static String getTime(long millis) {
        final DateFormat formatter = SimpleDateFormat.getTimeInstance();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return formatter.format(calendar.getTime());
    }
}
