package playduin.locationtracker.models.cache

import io.reactivex.rxjava3.core.Observable

interface Cache {
    fun trackerState(value: Boolean?)
    fun trackerState(): Boolean?
    fun trackerStateObservable(): Observable<Boolean>
    fun locationsCount(value: Int)
    fun locationsCount(): Int
    fun locationsCountObservable(): Observable<Int>
    fun locationAvailability(value: Boolean?)
    fun locationAvailabilityObservable(): Observable<Boolean>
    fun logoutDialogState(value: Int)
    fun logoutDialogObservable(): Observable<Int>
    fun datePickerState(value: Long)
    fun datePickerObservable(): Observable<Long>
}
