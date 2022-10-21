package playduin.locationtracker.models

import android.content.Context
import androidx.room.Room
import com.google.android.datatransport.runtime.dagger.Provides
import dagger.Module
import playduin.locationtracker.models.auth.AuthRepo
import playduin.locationtracker.models.auth.AuthRepoImpl
import playduin.locationtracker.models.auth.network.AuthNetwork
import playduin.locationtracker.models.auth.network.AuthNetworkImpl
import playduin.locationtracker.models.auth.storage.PasswordStorage
import playduin.locationtracker.models.auth.storage.PasswordStorageImpl
import playduin.locationtracker.models.cache.Cache
import playduin.locationtracker.models.cache.CacheImpl
import playduin.locationtracker.models.geo.LocationModel
import playduin.locationtracker.models.geo.LocationModelImpl
import playduin.locationtracker.models.locations.dao.AppDatabase
import playduin.locationtracker.models.locations.dao.LocationDao
import playduin.locationtracker.models.locations.map.MapLocationsRepository
import playduin.locationtracker.models.locations.map.MapLocationsRepositoryImpl
import playduin.locationtracker.models.locations.network.LocationsNetwork
import playduin.locationtracker.models.locations.network.LocationsNetworkImpl
import playduin.locationtracker.models.locations.tracker.TrackerLocationsRepository
import playduin.locationtracker.models.locations.tracker.TrackerLocationsRepositoryImpl
import javax.inject.Singleton

@Module
class DefaultAppModule(private var app: Context) : AppModule {
    private val authRepoValue: AuthRepo = AuthRepoImpl(passwordStorage(), authNetwork())
    private val locationModelValue: LocationModel = LocationModelImpl(app)
    private val cacheValue: Cache = CacheImpl()
    private val trackerDatabaseValue: LocationDao = app.let {
        Room.databaseBuilder(it,
                AppDatabase::class.java, "locations_tracker").build().locationDao()
    }!!
    private val mapDatabaseValue: LocationDao = app.let {
        Room.databaseBuilder(it,
                AppDatabase::class.java, "locations_map").build().locationDao()
    }!!
    private val locationsNetworkValue: LocationsNetwork = LocationsNetworkImpl()

    @Provides
    override fun app() = app

    @Provides
    @Singleton
    override fun authRepo(): AuthRepo = authRepoValue

    @Provides
    @Singleton
    override fun locationModel(): LocationModel = locationModelValue

    @Provides
    @Singleton
    override fun cache(): Cache = cacheValue

    @Provides
    @Singleton
    override fun trackerDatabase(): LocationDao = trackerDatabaseValue

    @Provides
    @Singleton
    override fun mapDatabase(): LocationDao = mapDatabaseValue

    @Provides
    @Singleton
    override fun locationsNetwork(): LocationsNetwork = locationsNetworkValue

    @Provides
    @Singleton
    fun passwordStorage(): PasswordStorage {
        return PasswordStorageImpl(app)
    }

    @Provides
    @Singleton
    fun authNetwork(): AuthNetwork {
        return AuthNetworkImpl()
    }

    @Provides
    override fun trackerRepository(): TrackerLocationsRepository {
        return TrackerLocationsRepositoryImpl(trackerDatabase(), locationsNetwork())
    }

    @Provides
    override fun mapRepository(): MapLocationsRepository {
        return MapLocationsRepositoryImpl(mapDatabase(), locationsNetwork())
    }
}
