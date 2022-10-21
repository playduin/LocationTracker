package playduin.locationtracker.models.geo

import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import playduin.locationtracker.BuildConfig
import playduin.locationtracker.models.locations.Location

class LocationModelImpl(private val context: android.content.Context) : LocationModel {
    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationAvailabilityObservable: BehaviorSubject<Boolean> = BehaviorSubject.create()
    private val locationObservable: BehaviorSubject<Location> = BehaviorSubject.create()
    private val locationManager: LocationManager = context.getSystemService(android.content.Context.LOCATION_SERVICE) as LocationManager
    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                locationObservable.onNext(Location(location.latitude, location.longitude, location.time))
            }
        }
    }
    private val locationListener: android.location.LocationListener = object : android.location.LocationListener {
        override fun onLocationChanged(location: android.location.Location) {}
        override fun onProviderEnabled(provider: String) {
            locationAvailabilityObservable.onNext(true)
        }

        override fun onProviderDisabled(provider: String) {
            locationAvailabilityObservable.onNext(false)
        }
    }

    override fun requestLocationAvailability() =
        locationAvailabilityObservable as Observable<Boolean>

    override fun requestLocation() = locationObservable as Observable<Location>

    override fun createLocationRequest() {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            throw Error("Permission not granted.")
        }
        val locationRequest: LocationRequest = LocationRequest.create()
        locationRequest.interval = BuildConfig.LOCATION_REQUEST_INTERVAL.toLong()
        locationRequest.fastestInterval = BuildConfig.LOCATION_REQUEST_FASTEST_INTERVAL.toLong()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.smallestDisplacement = BuildConfig.LOCATION_REQUEST_SMALLEST_DISPLACEMENT.toFloat()
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                BuildConfig.LOCATION_REQUEST_INTERVAL.toLong(), BuildConfig.LOCATION_REQUEST_SMALLEST_DISPLACEMENT.toFloat(),
                locationListener)
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                BuildConfig.LOCATION_REQUEST_INTERVAL.toLong(), BuildConfig.LOCATION_REQUEST_SMALLEST_DISPLACEMENT.toFloat(),
                locationListener)
        locationAvailabilityObservable.onNext(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
    }

    override fun removeLocationRequest() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
        locationManager.removeUpdates(locationListener)
    }
}
