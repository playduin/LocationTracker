package playduin.locationtracker.bg.di

import playduin.locationtracker.bg.LocationsJobScheduler
import playduin.locationtracker.models.cache.Cache
import playduin.locationtracker.models.geo.LocationModel
import playduin.locationtracker.models.locations.dao.LocationDao
import playduin.locationtracker.models.locations.network.LocationsNetwork
import playduin.locationtracker.models.locations.tracker.TrackerLocationsRepository

interface LocationServiceComponent {
    fun getLocationModel(): LocationModel
    fun getCache(): Cache
    fun getAppDatabase(): LocationDao
    fun getFirestoreModel(): LocationsNetwork
    fun getLocationsJobScheduler(): LocationsJobScheduler
    fun getLocationsRepository(): TrackerLocationsRepository
}