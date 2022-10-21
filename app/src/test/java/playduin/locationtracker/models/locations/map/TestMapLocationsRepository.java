package playduin.locationtracker.models.locations.map;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import playduin.locationtracker.models.locations.Location;
import playduin.locationtracker.models.locations.dao.LocationDao;
import playduin.locationtracker.models.locations.network.LocationsNetwork;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class TestMapLocationsRepository {
    private static final long DAY_LENGTH = 24 * 60 * 60 * 1000;

    private LocationDao dao;
    private LocationsNetwork network;

    private MapLocationsRepository mapLocationsRepository;

    @Before
    public void testSetup() {
        dao = mock(LocationDao.class);
        network = mock(LocationsNetwork.class);

        mapLocationsRepository = new MapLocationsRepositoryImpl(dao, network);
    }

    @Test
    public void testUpdateLocations() {
        List<Location> locations = new ArrayList<>();
        locations.add(new Location(0.0, 1.23, 123456));
        locations.add(new Location(1.23, 1.23, 1234567));
        locations.add(new Location(1.23, 0.0, 12345678));

        List<Location> lastLocation = new ArrayList<>();
        lastLocation.add(new Location(0.0, 1.23, 123456));

        when(dao.getLastLocation()).thenReturn(lastLocation);
        when(network.getLocations(123456)).thenReturn(Single.just(locations));
        when(dao.getAll()).thenReturn(locations);

        boolean[] subscribed = {false};
        Disposable disposable = mapLocationsRepository.updateLocations().subscribe(locations1 -> {
            assertEquals(locations1, locations);
            subscribed[0] = true;
        });
        assertTrue(subscribed[0]);

        verify(dao).getLastLocation();
        verify(network).getLocations(123456);
        verify(dao).insert(locations);
        verify(dao).getAll();

        verifyNoMoreInteractionsInModels();

        disposable.dispose();
    }

    @Test
    public void testUpdateLocationsWithoutLocations() {
        List<Location> locations = new ArrayList<>();
        locations.add(new Location(0.0, 1.23, 123456));
        locations.add(new Location(1.23, 1.23, 1234567));
        locations.add(new Location(1.23, 0.0, 12345678));

        List<Location> lastLocation = new ArrayList<>();

        when(dao.getLastLocation()).thenReturn(lastLocation);
        when(network.getLocations(0)).thenReturn(Single.just(locations));
        when(dao.getAll()).thenReturn(locations);

        boolean[] subscribed = {false};
        Disposable disposable = mapLocationsRepository.updateLocations().subscribe(locations1 -> {
            assertEquals(locations1, locations);
            subscribed[0] = true;
        });
        assertTrue(subscribed[0]);

        verify(dao).getLastLocation();
        verify(network).getLocations(0);
        verify(dao).insert(locations);
        verify(dao).getAll();

        verifyNoMoreInteractionsInModels();

        disposable.dispose();
    }

    @Test
    public void testGetLocationsForAnyDate() {
        List<Location> locations = new ArrayList<>();
        locations.add(new Location(0.0, 1.23, 123456));
        locations.add(new Location(1.23, 1.23, 1234567));
        locations.add(new Location(1.23, 0.0, 12345678));

        List<Location> lastLocation = new ArrayList<>();

        when(dao.getLastLocation()).thenReturn(lastLocation);
        when(network.getLocations(0)).thenReturn(Single.just(locations));
        when(dao.getAll()).thenReturn(locations);

        boolean[] subscribed = {false};
        Disposable disposable = mapLocationsRepository.getLocationsForDate(MapLocationsRepository.ANY_DATE).subscribe(locations1 -> {
            assertEquals(locations1, locations);
            subscribed[0] = true;
        });
        assertTrue(subscribed[0]);

        verify(dao).getLastLocation();
        verify(network).getLocations(0);
        verify(dao).insert(locations);
        verify(dao).getAll();

        verifyNoMoreInteractionsInModels();

        disposable.dispose();
    }

    @Test
    public void testGetLocationsForDate() {
        List<Location> locations = new ArrayList<>();
        locations.add(new Location(1.23, 1.23, 1234567));
        locations.add(new Location(1.23, 0.0, 12345678));

        when(dao.getLocationsByTime(1234567, 1234567 + DAY_LENGTH)).thenReturn(locations);

        boolean[] subscribed = {false};
        Disposable disposable = mapLocationsRepository.getLocationsForDate(1234567).subscribe(locations1 -> {
            assertEquals(locations1, locations);
            subscribed[0] = true;
        });
        assertTrue(subscribed[0]);

        verify(dao).getLocationsByTime(1234567, 1234567 + DAY_LENGTH);

        verifyNoMoreInteractionsInModels();

        disposable.dispose();
    }

    private void verifyNoMoreInteractionsInModels() {
        verifyNoMoreInteractions(dao);
        verifyNoMoreInteractions(network);
    }
}
