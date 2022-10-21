package playduin.locationtracker.ui

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.Navigation
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import playduin.locationtracker.R
import playduin.locationtracker.bg.LocationService
import playduin.locationtracker.ui.login.LoginContract
import playduin.locationtracker.ui.logout.LogoutDialogFragment
import playduin.locationtracker.ui.registration.RegistrationContract
import playduin.locationtracker.ui.splash.SplashContract
import playduin.locationtracker.ui.tracker.TrackerContract

class TrackerActivity : AppCompatActivity(), SplashContract.Host, LoginContract.Host, RegistrationContract.Host, TrackerContract.Host {
    private val permissionGrantObservable: BehaviorSubject<String> = BehaviorSubject.create()

    override fun getPermissionGrantObservable() = permissionGrantObservable as Observable<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tracker_activity)
    }

    override fun proceedSplashScreenToLoginScreen() {
        Navigation.findNavController(this, R.id.tracker_nav_host_fragment)
            .navigate(R.id.action_splashFragment_to_loginFragment)
    }

    override fun proceedSplashScreenToTrackerScreen() {
        Navigation.findNavController(this, R.id.tracker_nav_host_fragment).navigate(R.id.action_splashFragment_to_trackerFragment)
    }

    override fun proceedSplashScreenToMapScreen() {
        Navigation.findNavController(this, R.id.tracker_nav_host_fragment).navigate(R.id.action_splashFragment_to_mapFragment)
    }

    override fun startTracker() {
        startService(LocationService.newIntent(this))
    }

    override fun stopTracker() {
        stopService(LocationService.newIntent(this))
    }

    override fun showLogoutAlertDialog() {
        LogoutDialogFragment().show(supportFragmentManager, LogoutDialogFragment.TAG)
    }

    override fun proceedTrackerScreenToLoginScreen() {
        Navigation.findNavController(this, R.id.tracker_nav_host_fragment).navigate(R.id.action_trackerFragment_to_loginFragment)
    }

    override fun proceedLoginScreenToRegistrationScreen() {
        Navigation.findNavController(this, R.id.tracker_nav_host_fragment).navigate(R.id.action_loginFragment_to_registrationFragment)
    }

    override fun proceedLoginScreenToTrackerScreen() {
        Navigation.findNavController(this, R.id.tracker_nav_host_fragment).navigate(R.id.action_loginFragment_to_trackerFragment)
    }

    override fun proceedLoginScreenToMapScreen() {
        Navigation.findNavController(this, R.id.tracker_nav_host_fragment).navigate(R.id.action_loginFragment_to_mapFragment)
    }

    override fun proceedRegistrationScreenToLoginScreen() {
        Navigation.findNavController(this, R.id.tracker_nav_host_fragment).navigate(R.id.action_registrationFragment_to_loginFragment)
    }

    override fun proceedRegistrationScreenToTrackerScreen() {
        Navigation.findNavController(this, R.id.tracker_nav_host_fragment).navigate(R.id.action_registrationFragment_to_trackerFragment)
    }

    override fun proceedRegistrationScreenToMapScreen() {
        Navigation.findNavController(this, R.id.tracker_nav_host_fragment).navigate(R.id.action_registrationFragment_to_mapFragment)
    }

    override fun requestPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), 1)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    permissionGrantObservable.onNext(permissions[i])
                }
            }
        }
    }
}
