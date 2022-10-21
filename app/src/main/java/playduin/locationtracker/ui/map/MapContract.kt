package playduin.locationtracker.ui.map

import com.google.android.gms.maps.model.LatLng
import playduin.locationtracker.models.locations.Marker
import playduin.locationtracker.ui.mvi.FragmentContract

class MapContract {
    interface ViewModel : FragmentContract.ViewModel<MapScreenState, MapScreenAction> {
        fun createMarkers()
        fun logout()
    }

    interface View : FragmentContract.View {
        fun mapAddMarkers(markers: List<Marker>)
        fun mapMoveCamera(pos: LatLng?)
        fun mapClear()
        fun showLoginFragment()
    }

    interface Host : FragmentContract.Host {
        fun showDatePickerDialog()
        fun proceedMapScreenToLoginScreen()
    }
}
