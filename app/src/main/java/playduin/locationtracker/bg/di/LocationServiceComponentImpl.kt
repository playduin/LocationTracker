package playduin.locationtracker.bg.di

import dagger.Module
import dagger.Provides
import playduin.locationtracker.bg.LocationsJobScheduler
import playduin.locationtracker.models.AppModule
import javax.inject.Inject

@Module
class LocationServiceComponentImpl @Inject constructor(
    private val appModule: AppModule, private val locationServiceCtx: LocationsJobScheduler
) : LocationServiceComponent {

    @Provides
    override fun getLocationModel() = appModule.locationModel()

    @Provides
    override fun getCache() = appModule.cache()

    @Provides
    override fun getAppDatabase() = appModule.trackerDatabase()

    @Provides
    override fun getFirestoreModel() = appModule.locationsNetwork()

    @Provides
    override fun getLocationsJobScheduler() = locationServiceCtx

    @Provides
    override fun getLocationsRepository() = appModule.trackerRepository()
}
