package playduin.locationtracker.ui.map

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.MarkerOptions
import playduin.locationtracker.R
import playduin.locationtracker.models.locations.Marker
import playduin.locationtracker.ui.mvi.HostedFragment

class MapFragment : HostedFragment<MapContract.View, MapScreenState, MapScreenAction, MapContract.ViewModel, MapContract.Host>(), MapContract.View, OnMapReadyCallback {
    private var map: GoogleMap? = null
    private var mapView: MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.map_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mapView = view.findViewById(R.id.map_view)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.map, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (map != null && R.id.sync_btn == item.itemId) {
            model?.createMarkers()
        } else if (R.id.date_select_btn == item.itemId) {
            fragmentHost?.showDatePickerDialog()
        } else if (R.id.sign_out_btn == item.itemId) {
            model?.logout()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun createModel(): MapContract.ViewModel {
        return ViewModelProvider(this, MapViewModelFactory())
                .get(MapViewModel::class.java)
    }

    override fun mapAddMarkers(markers: List<Marker>) {
        map?.let {
            for (marker in markers) {
                it.addMarker(
                        MarkerOptions()
                                .position(marker.location)
                                .title(marker.text)
                )
            }
        }
    }

    override fun mapMoveCamera(pos: com.google.android.gms.maps.model.LatLng?) {
        map?.moveCamera(CameraUpdateFactory.newLatLng(pos!!))
    }

    override fun mapClear() {
        map?.clear()
    }

    override fun showLoginFragment() {
        fragmentHost?.proceedMapScreenToLoginScreen()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        model?.createMarkers()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        mapView?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapView?.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }
}