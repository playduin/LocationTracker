package playduin.locationtracker.models.locations.network

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import playduin.locationtracker.models.locations.Location

interface LocationsNetwork {
    fun getLocations(startTime: Long): Single<List<Location>>
    fun sendLocation(locations: List<Location>): Completable
}
