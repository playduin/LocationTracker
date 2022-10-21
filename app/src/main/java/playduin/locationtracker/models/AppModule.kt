package playduin.locationtracker.models

import playduin.locationtracker.models.auth.AuthRepo
import playduin.locationtracker.models.cache.Cache
import playduin.locationtracker.models.geo.LocationModel
import playduin.locationtracker.models.locations.dao.LocationDao
import playduin.locationtracker.models.locations.map.MapLocationsRepository
import playduin.locationtracker.models.locations.network.LocationsNetwork
import playduin.locationtracker.models.locations.tracker.TrackerLocationsRepository

interface AppModule {
    fun app(): android.content.Context
    fun authRepo(): AuthRepo
    fun locationModel(): LocationModel
    fun cache(): Cache
    fun trackerDatabase(): LocationDao
    fun mapDatabase(): LocationDao
    fun locationsNetwork(): LocationsNetwork
    fun trackerRepository(): TrackerLocationsRepository
    fun mapRepository(): MapLocationsRepository
}