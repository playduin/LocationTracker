package playduin.locationtracker.bg;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import playduin.locationtracker.models.cache.Cache;
import playduin.locationtracker.models.geo.LocationModel;
import playduin.locationtracker.models.locations.Location;
import playduin.locationtracker.models.locations.tracker.TrackerLocationsRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class TestLocationServiceModel {
    private static final int TEST_SLEEP = 1000;

    private LocationModel locationModel;
    private Cache cache;
    private TrackerLocationsRepository trackerLocationsRepository;
    private LocationsJobScheduler locationsJobScheduler;

    @Before
    public void testSetup() {
        locationModel = mock(LocationModel.class);
        cache = mock(Cache.class);
        trackerLocationsRepository = mock(TrackerLocationsRepository.class);
        locationsJobScheduler = mock(LocationsJobScheduler.class);
    }

    @Test
    public void testStart() {
        Location location = new Location(0.0, 1.23, 123456789);

        BehaviorSubject<Boolean> locationAvailabilityObservable = BehaviorSubject.create();
        BehaviorSubject<Location> locationObservable = BehaviorSubject.create();

        when(locationModel.requestLocationAvailability()).thenReturn(locationAvailabilityObservable);
        when(locationModel.requestLocation()).thenReturn(locationObservable);

        when(trackerLocationsRepository.save(location)).thenReturn(Completable.complete());
        when(trackerLocationsRepository.syncRemote()).thenReturn(Completable.complete());
        when(trackerLocationsRepository.clear()).thenReturn(Completable.complete());

        when(cache.getLocationsCount()).thenReturn(100);

        locationAvailabilityObservable.onNext(true);
        locationObservable.onNext(location);

        LocationServiceModel locationServiceModel = new LocationServiceModel(locationModel, cache, trackerLocationsRepository, locationsJobScheduler);

        locationServiceModel.start();

        try {
            Thread.sleep(TEST_SLEEP);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        verify(cache).setTrackerState(true);
        verify(locationModel).createLocationRequest();
        verify(locationModel).requestLocationAvailability();
        verify(locationModel).requestLocation();
        verify(cache).setLocationAvailability(true);
        verify(trackerLocationsRepository).save(location);
        verify(trackerLocationsRepository).syncRemote();
        verify(cache).getLocationsCount();
        verify(cache).setLocationsCount(101);
        verify(trackerLocationsRepository).clear();

        verifyNoMoreInteractionsInModels();
    }

    @Test
    public void testStop() {
        LocationServiceModel locationServiceModel = new LocationServiceModel(locationModel, cache, trackerLocationsRepository, locationsJobScheduler);

        locationServiceModel.stop();

        verify(cache).setTrackerState(false);
        verify(locationModel).removeLocationRequest();

        verifyNoMoreInteractionsInModels();
    }

    @Test
    public void testGetLocationAvailabilityObservable() {
        BehaviorSubject<Boolean> locationAvailabilityObservable = BehaviorSubject.create();
        when(locationModel.requestLocationAvailability()).thenReturn(locationAvailabilityObservable);

        LocationServiceModel locationServiceModel = new LocationServiceModel(locationModel, cache, trackerLocationsRepository, locationsJobScheduler);

        assertEquals(locationServiceModel.getLocationAvailabilityObservable(), locationAvailabilityObservable);

        verify(locationModel).requestLocationAvailability();

        verifyNoMoreInteractionsInModels();
    }

    private void verifyNoMoreInteractionsInModels() {
        verifyNoMoreInteractions(locationModel);
        verifyNoMoreInteractions(cache);
        verifyNoMoreInteractions(trackerLocationsRepository);
        verifyNoMoreInteractions(locationsJobScheduler);
    }
}
