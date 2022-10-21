package playduin.locationtracker.models.locations.map

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import playduin.locationtracker.models.locations.Location
import playduin.locationtracker.models.locations.dao.LocationDao
import playduin.locationtracker.models.locations.map.MapLocationsRepository.Companion.ANY_DATE
import playduin.locationtracker.models.locations.network.LocationsNetwork

class MapLocationsRepositoryImpl(
    private val dao: LocationDao, private val network: LocationsNetwork
) : MapLocationsRepository {

    override fun updateLocations(): Single<List<Location>> {
        return Single.fromCallable(dao::getLastLocation)
            .flatMap { locations: List<Location> ->
                network.getLocations(
                    if (locations.isEmpty()) {
                        0
                    } else {
                        locations[0].time
                    }
                )
                    .flatMapCompletable { newLocations ->
                        Completable.fromAction { dao.insert(newLocations) }
                    }
                    .andThen(Single.fromCallable(dao::getAll))
            }
    }

    override fun getLocationsForDate(date: Long): Single<List<Location>> {
        return if (ANY_DATE == date) {
            updateLocations()
        } else {
            Single.fromCallable { dao.getLocationsByTime(date, date + DAY_LENGTH) }
        }
    }

    companion object {
        private const val DAY_LENGTH = (24 * 60 * 60 * 1000).toLong()
    }
}
