package playduin.locationtracker.models.locations.tracker;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.Disposable;
import playduin.locationtracker.models.locations.Location;
import playduin.locationtracker.models.locations.dao.LocationDao;
import playduin.locationtracker.models.locations.network.LocationsNetwork;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class TestTrackerLocationsRepository {
    private LocationDao dao;
    private LocationsNetwork network;

    private TrackerLocationsRepository trackerLocationsRepository;

    @Before
    public void testSetup() {
        dao = mock(LocationDao.class);
        network = mock(LocationsNetwork.class);

        trackerLocationsRepository = new TrackerLocationsRepositoryImpl(dao, network);
    }

    @Test
    public void testSave() {
        Location location = new Location(0.0, 1.23, 123456);

        boolean[] subscribed = {false};
        Disposable disposable = trackerLocationsRepository.save(location).subscribe(() -> subscribed[0] = true);
        assertTrue(subscribed[0]);

        verify(dao).insert(location);

        verifyNoMoreInteractionsInModels();

        disposable.dispose();
    }

    @Test
    public void testSyncRemote() {
        List<Location> locations = new ArrayList<>();
        locations.add(new Location(0.0, 1.23, 123456));
        locations.add(new Location(1.23, 1.23, 1234567));
        locations.add(new Location(1.23, 0.0, 12345678));

        when(dao.getAll()).thenReturn(locations);
        when(network.sendLocation(locations)).thenReturn(Completable.complete());

        boolean[] subscribed = {false};
        Disposable disposable = trackerLocationsRepository.syncRemote().subscribe(() -> subscribed[0] = true);
        assertTrue(subscribed[0]);

        verify(dao).getAll();
        verify(network).sendLocation(locations);

        verifyNoMoreInteractionsInModels();

        disposable.dispose();
    }

    @Test
    public void testSyncRemoteWithEmptyLocations() {
        List<Location> locations = new ArrayList<>();

        when(dao.getAll()).thenReturn(locations);

        boolean[] subscribed = {false};
        Disposable disposable = trackerLocationsRepository.syncRemote().subscribe(() -> subscribed[0] = true);
        assertTrue(subscribed[0]);

        verify(dao).getAll();

        verifyNoMoreInteractionsInModels();

        disposable.dispose();
    }

    @Test
    public void testClear() {
        boolean[] subscribed = {false};
        Disposable disposable = trackerLocationsRepository.clear().subscribe(() -> subscribed[0] = true);
        assertTrue(subscribed[0]);

        verify(dao).deleteAll();

        verifyNoMoreInteractionsInModels();

        disposable.dispose();
    }

    private void verifyNoMoreInteractionsInModels() {
        verifyNoMoreInteractions(dao);
        verifyNoMoreInteractions(network);
    }
}
