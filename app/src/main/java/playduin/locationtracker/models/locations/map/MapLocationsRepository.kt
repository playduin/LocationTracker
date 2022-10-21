package playduin.locationtracker.models.locations.map

import io.reactivex.rxjava3.core.Single
import playduin.locationtracker.models.locations.Location

interface MapLocationsRepository {
    fun updateLocations(): Single<List<Location>>
    fun getLocationsForDate(date: Long): Single<List<Location>>

    companion object {
        const val ANY_DATE = -1L
    }
}
