package playduin.locationtracker.models.geo

import io.reactivex.rxjava3.core.Observable
import playduin.locationtracker.models.locations.Location

interface LocationModel {
    fun requestLocationAvailability(): Observable<Boolean>
    fun requestLocation(): Observable<Location>
    fun createLocationRequest()
    fun removeLocationRequest()
}
