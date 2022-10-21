package playduin.locationtracker.models.locations.tracker

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.Function
import playduin.locationtracker.models.locations.Location
import playduin.locationtracker.models.locations.dao.LocationDao
import playduin.locationtracker.models.locations.network.LocationsNetwork

class TrackerLocationsRepositoryImpl(private val dao: LocationDao, private val network: LocationsNetwork) : TrackerLocationsRepository {

    override fun save(location: Location): Completable =
        Completable.fromAction { dao.insert(location) }

    override fun syncRemote(): Completable {
        return Single.fromCallable(dao::getAll)
            .flatMapCompletable(Function { list: List<Location> ->
                if (list.isEmpty()) {
                    return@Function Completable.complete()
                } else {
                    return@Function network.sendLocation(list)
                }
            })
    }

    override fun clear(): Completable = Completable.fromAction(dao::deleteAll)
}