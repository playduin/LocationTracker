package playduin.locationtracker.models.locations.tracker

import io.reactivex.rxjava3.core.Completable
import playduin.locationtracker.models.locations.Location

interface TrackerLocationsRepository {
    fun save(location: Location): Completable
    fun syncRemote(): Completable
    fun clear(): Completable
}
