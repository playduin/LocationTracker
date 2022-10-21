package playduin.locationtracker.ui.map

import androidx.core.util.Pair
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import playduin.locationtracker.models.auth.AuthRepo
import playduin.locationtracker.models.cache.Cache
import playduin.locationtracker.models.locations.Location
import playduin.locationtracker.models.locations.Marker
import playduin.locationtracker.models.locations.map.MapLocationsRepository
import playduin.locationtracker.ui.mvi.BaseViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val authRepo: AuthRepo,
    private val repo: MapLocationsRepository,
    private val cache: Cache
) : BaseViewModel<MapScreenState, MapScreenAction>(), MapContract.ViewModel {

    private var launch: Job? = null

    private val updateLocationsObservable: BehaviorSubject<Long> =
        BehaviorSubject.createDefault(MapLocationsRepository.ANY_DATE)
    private var isSubscribed = false

    override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?) {
        super.onAny(owner, event)
        if (event == Lifecycle.Event.ON_START && !isSubscribed) {
            disposables.add(
                Observable.merge(cache.datePickerObservable(), updateLocationsObservable)
                    .flatMap { date ->
                        repo.getLocationsForDate(date)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .toObservable()
                    }
                    .doOnNext { setState(MapScreenState.ClearMap()) }
                    .map { locations ->
                        if (locations.isEmpty()) {
                            return@map Pair(ArrayList<Marker>(), LatLng(0.0, 0.0))
                        } else {
                            val lastLocation: Location = locations[locations.size - 1]
                            val lastPos = LatLng(lastLocation.latitude, lastLocation.longitude)
                            return@map Pair(getMarkers(locations), lastPos)
                        }
                    }
                    .subscribe { pair ->
                        pair.first.let {
                            if (pair.first.isNotEmpty()) {
                                setState(MapScreenState.AddMarkers(pair.first))
                                setState(MapScreenState.MoveCamera(pair.second))
                            }
                        }
                    })
            isSubscribed = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        launch?.cancel()
        isSubscribed = false
    }

    override fun createMarkers() {
        updateLocationsObservable.onNext(MapLocationsRepository.ANY_DATE)
    }

    override fun logout() {
        launch = viewModelScope.launch { authRepo.signOut().collect { setAction(MapScreenAction.ShowLoginFragment()) } }
    }

    private fun getMarkers(locations: List<Location>): List<Marker> {
        return locations.map {
            Marker(LatLng(it.latitude, it.longitude), getDate(it.time) + " " + getTime(it.time))
        }
    }

    companion object {
        private fun getDate(millis: Long): String {
            val formatter = SimpleDateFormat.getDateInstance()
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = millis
            return formatter.format(calendar.time)
        }

        private fun getTime(millis: Long): String {
            val formatter = SimpleDateFormat.getTimeInstance()
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = millis
            return formatter.format(calendar.time)
        }
    }
}
