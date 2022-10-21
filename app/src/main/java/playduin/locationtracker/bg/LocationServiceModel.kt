package playduin.locationtracker.bg

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import playduin.locationtracker.models.cache.Cache
import playduin.locationtracker.models.geo.LocationModel
import playduin.locationtracker.models.locations.tracker.TrackerLocationsRepository
import javax.inject.Inject

class LocationServiceModel @Inject constructor(
    private val locationModel: LocationModel, private val cache: Cache,
    private val repo: TrackerLocationsRepository, private val scheduler: LocationsJobScheduler
) {
    private val disposables: CompositeDisposable = CompositeDisposable()

    fun start() {
        cache.trackerState(true)
        locationModel.createLocationRequest()
        disposables.add(
            locationModel.requestLocationAvailability().subscribe(cache::locationAvailability)
        )
        disposables.add(locationModel.requestLocation()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .flatMapCompletable { location ->
                repo.save(location)
                    .andThen(repo.syncRemote())
                    .andThen(object : Completable() {
                        override fun subscribeActual(observer: @NonNull CompletableObserver?) {
                            cache.locationsCount(cache.locationsCount().plus(1))
                        }
                    })
                    .andThen(repo.clear())
                    .onErrorResumeWith { scheduler.scheduleSendJob() }
                    .onErrorComplete()
            }
            .subscribe({}) { scheduler.scheduleSendJob() })
    }

    fun stop() {
        cache.trackerState(false)
        locationModel.removeLocationRequest()
        disposables.dispose()
    }

    fun getLocationAvailabilityObservable() = locationModel.requestLocationAvailability()
}
