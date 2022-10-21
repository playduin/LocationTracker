package playduin.locationtracker.ui.tracker

import androidx.core.content.PermissionChecker
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import playduin.locationtracker.R
import playduin.locationtracker.models.auth.AuthRepo
import playduin.locationtracker.models.cache.Cache
import playduin.locationtracker.models.locations.tracker.TrackerLocationsRepository
import playduin.locationtracker.ui.logout.LogoutDialogState
import playduin.locationtracker.ui.mvi.BaseViewModel
import javax.inject.Inject

class TrackerViewModel @Inject constructor(
    private val authRepo: AuthRepo,
    private val cache: Cache,
    private val repo: TrackerLocationsRepository
) : BaseViewModel<TrackerScreenState, TrackerScreenAction>(), TrackerContract.ViewModel {

    private var launch: Job? = null

    override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?) {
        super.onAny(owner, event)
        if (event == Lifecycle.Event.ON_START) {
            disposables.add(cache.trackerStateObservable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe { tracking: Boolean -> setServiceRunningState(tracking) })
            disposables.add(cache.locationAvailabilityObservable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe { state: Boolean -> setLocationAvailability(state) })
            disposables.add(cache.locationsCountObservable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe { count: Int -> setLocationsCount(count) })
            if (cache.trackerState() == true) {
                setState(
                    TrackerScreenState.ServiceRunning(
                        R.string.stop, R.string.tracker_is_started
                    )
                )
            } else {
                setState(
                    TrackerScreenState.ServiceStopped(
                        R.string.start, R.string.tracker_is_not_started
                    )
                )
            }
        }
    }

    override fun permissionGrantSubscribe(permissionGrantObservable: Observable<String>) {
        disposables.add(permissionGrantObservable.subscribe { permission: String ->
            if (android.Manifest.permission.ACCESS_COARSE_LOCATION == permission) {
                setAction(TrackerScreenAction.StartTracker())
                setState(
                        TrackerScreenState.ServiceRunning(
                                R.string.stop,
                                R.string.tracker_is_started
                        )
                )
            }
        })
    }

    override fun toggleTracker(permissionState: Int) {
        when {
            cache.trackerState() == true -> {
                setAction(TrackerScreenAction.StopTracker())
                setState(
                    TrackerScreenState.ServiceStopped(
                        R.string.start, R.string.tracker_is_not_started
                    )
                )
            }
            PermissionChecker.PERMISSION_GRANTED == permissionState -> {
                setAction(TrackerScreenAction.StartTracker())
                setState(
                    TrackerScreenState.ServiceRunning(
                        R.string.stop, R.string.tracker_is_started
                    )
                )
            }
            else -> setAction(TrackerScreenAction.RequestPermission())
        }
    }

    override fun logout() {
        if (cache.trackerState() == true) {
            setAction(TrackerScreenAction.StopTracker())
        }
        disposables.add(
                repo.syncRemote()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .toSingleDefault(true)
                        .onErrorReturnItem(false)
                        .flatMap { isSuccess ->
                            if (isSuccess) {
                                return@flatMap Single.just(LogoutDialogState.SENT_LOCATIONS_STATE)
                            } else {
                                setAction(TrackerScreenAction.ShowLogoutAlertDialog())
                                return@flatMap cache.logoutDialogObservable().firstOrError()
                            }
                        }.flatMapCompletable { state ->
                            when (state) {
                                LogoutDialogState.CLEAR_LOCATIONS_STATE -> {
                                    return@flatMapCompletable Completable.complete() // repo.clear()
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .andThen { launch = viewModelScope.launch { authRepo.signOut().collect { } } }
                                }
                                LogoutDialogState.WAIT_CONNECTION_STATE -> {
                                    return@flatMapCompletable Completable.error(java.lang.Exception())
                                }
                                else -> return@flatMapCompletable Completable.fromCallable {
                                    launch = viewModelScope.launch { authRepo.signOut().collect { } }
                                    Completable.complete()
                                }
                            }
                        }.subscribe({ setAction(TrackerScreenAction.ShowLoginFragment()) }) { })
    }

    private fun setServiceRunningState(tracking: Boolean) {
        if (tracking) {
            setState(
                TrackerScreenState.ServiceRunning(
                    R.string.stop, R.string.tracker_is_started
                )
            )
        } else {
            setState(
                TrackerScreenState.ServiceStopped(
                    R.string.start, R.string.tracker_is_not_started
                )
            )
        }
    }

    private fun setLocationAvailability(state: Boolean) {
        setState(TrackerScreenState.SetGPSState(state))
    }

    private fun setLocationsCount(count: Int) {
        setState(TrackerScreenState.SetLocationsCount(count))
    }

    override fun onCleared() {
        super.onCleared()
        launch?.cancel()
    }
}