package playduin.locationtracker.models.cache

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

class CacheImpl : Cache {
    private val trackerStateObservable: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    private val locationsCountObservable: BehaviorSubject<Int> = BehaviorSubject.createDefault(0)
    private val locationAvailabilityObservable: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    private val logoutDialogObservable: PublishSubject<Int> = PublishSubject.create()
    private val datePickerObservable: BehaviorSubject<Long> = BehaviorSubject.create()

    override fun trackerState(value: Boolean?) {
        trackerStateObservable.onNext(value)
    }

    override fun trackerState(): Boolean? {
        return trackerStateObservable.value
    }

    override fun trackerStateObservable(): Observable<Boolean> {
        return trackerStateObservable.hide()
    }

    override fun locationsCount(value: Int) {
        locationsCountObservable.onNext(value)
    }

    override fun locationsCount(): Int {
        return locationsCountObservable.value ?: 0
    }

    override fun locationsCountObservable(): Observable<Int> {
        return locationsCountObservable.hide()
    }

    override fun locationAvailability(value: Boolean?) {
        locationAvailabilityObservable.onNext(value)
    }

    override fun locationAvailabilityObservable(): Observable<Boolean> {
        return locationAvailabilityObservable.hide()
    }

    override fun logoutDialogState(value: Int) {
        logoutDialogObservable.onNext(value)
    }

    override fun logoutDialogObservable(): Observable<Int> {
        return logoutDialogObservable.hide()
    }

    override fun datePickerState(value: Long) {
        datePickerObservable.onNext(value)
    }

    override fun datePickerObservable(): Observable<Long> {
        return datePickerObservable.hide()
    }
}
