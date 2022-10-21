package playduin.locationtracker.models;

import android.content.Context;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import playduin.locationtracker.models.auth.AuthRepo;
import playduin.locationtracker.models.auth.AuthRepoImpl;
import playduin.locationtracker.models.auth.network.AuthNetwork;
import playduin.locationtracker.models.auth.network.TestAuthNetwork;
import playduin.locationtracker.models.auth.storage.PasswordStorage;
import playduin.locationtracker.models.auth.storage.PasswordStorageImpl;
import playduin.locationtracker.models.cache.Cache;
import playduin.locationtracker.models.cache.CacheImpl;
import playduin.locationtracker.models.geo.LocationModel;
import playduin.locationtracker.models.geo.TestLocationModel;
import playduin.locationtracker.models.locations.dao.AppDatabase;
import playduin.locationtracker.models.locations.dao.LocationDao;
import playduin.locationtracker.models.locations.map.MapLocationsRepository;
import playduin.locationtracker.models.locations.map.TestMapLocationsRepository;
import playduin.locationtracker.models.locations.network.LocationsNetwork;
import playduin.locationtracker.models.locations.network.TestLocationsNetwork;
import playduin.locationtracker.models.locations.tracker.TrackerLocationsRepository;
import playduin.locationtracker.models.locations.tracker.TrackerLocationsRepositoryImpl;

@Module
public class TestAppModule implements AppModule {
    public final TestAuthNetwork authNetwork;
    public final TestLocationModel locationModel;
    public final TestLocationsNetwork locationsNetwork;
    public final TestMapLocationsRepository mapLocationsRepository;

    private final Context app;
    private final AuthRepo authRepo;
    private final Cache cache;
    private final LocationDao trackerDatabase;
    private final LocationDao mapDatabase;

    public TestAppModule(Context app) {
        this.app = app;
        locationModel = new TestLocationModel();
        cache = new CacheImpl();
        authNetwork = new TestAuthNetwork();
        authRepo = new AuthRepoImpl(getPasswordStorage(), getAuthNetwork());
        trackerDatabase = Room.databaseBuilder(app,
                AppDatabase.class, "locations_tracker").build().locationDao();
        mapDatabase = Room.databaseBuilder(app,
                AppDatabase.class, "locations_map").build().locationDao();
        locationsNetwork = new TestLocationsNetwork();
        mapLocationsRepository = new TestMapLocationsRepository();
    }

    @Provides
    public Context getApp() {
        return app;
    }

    @Provides
    @Singleton
    public AuthRepo getAuthRepo() {
        return authRepo;
    }

    @Provides
    @Singleton
    public LocationModel getLocationModel() {
        return locationModel;
    }

    @Provides
    @Singleton
    public PasswordStorage getPasswordStorage() {
        return new PasswordStorageImpl(app);
    }

    @Provides
    @Singleton
    public AuthNetwork getAuthNetwork() {
        return authNetwork;
    }

    @Provides
    @Singleton
    public Cache getCache() {
        return cache;
    }

    @Provides
    @Singleton
    public LocationDao getTrackerDatabase() {
        return trackerDatabase;
    }

    @Provides
    @Singleton
    public LocationDao getMapDatabase() {
        return mapDatabase;
    }

    @Provides
    @Singleton
    public LocationsNetwork getLocationsNetwork() {
        return locationsNetwork;
    }

    @Provides
    @Override
    public TrackerLocationsRepository getTrackerRepository() {
        return new TrackerLocationsRepositoryImpl(getTrackerDatabase(), getLocationsNetwork());
    }

    @Provides
    @Override
    public MapLocationsRepository getMapRepository() {
        return mapLocationsRepository;
    }
}
