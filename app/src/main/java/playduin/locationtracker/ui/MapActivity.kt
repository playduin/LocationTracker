package playduin.locationtracker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import playduin.locationtracker.R
import playduin.locationtracker.ui.date.DatePickerFragment
import playduin.locationtracker.ui.login.LoginContract
import playduin.locationtracker.ui.map.MapContract
import playduin.locationtracker.ui.registration.RegistrationContract
import playduin.locationtracker.ui.splash.SplashContract

class MapActivity : AppCompatActivity(), SplashContract.Host, LoginContract.Host, RegistrationContract.Host, MapContract.Host {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_activity)
    }

    override fun proceedSplashScreenToLoginScreen() {
        Navigation.findNavController(this, R.id.map_nav_host_fragment).navigate(R.id.action_splashFragment_to_loginFragment)
    }

    override fun proceedSplashScreenToTrackerScreen() {
        Navigation.findNavController(this, R.id.map_nav_host_fragment).navigate(R.id.action_splashFragment_to_trackerFragment)
    }

    override fun proceedSplashScreenToMapScreen() {
        Navigation.findNavController(this, R.id.map_nav_host_fragment).navigate(R.id.action_splashFragment_to_mapFragment)
    }

    override fun proceedLoginScreenToRegistrationScreen() {
        Navigation.findNavController(this, R.id.map_nav_host_fragment).navigate(R.id.action_loginFragment_to_registrationFragment)
    }

    override fun proceedLoginScreenToTrackerScreen() {
        Navigation.findNavController(this, R.id.map_nav_host_fragment).navigate(R.id.action_loginFragment_to_trackerFragment)
    }

    override fun proceedLoginScreenToMapScreen() {
        Navigation.findNavController(this, R.id.map_nav_host_fragment).navigate(R.id.action_loginFragment_to_mapFragment)
    }

    override fun proceedRegistrationScreenToLoginScreen() {
        Navigation.findNavController(this, R.id.map_nav_host_fragment).navigate(R.id.action_registrationFragment_to_loginFragment)
    }

    override fun proceedRegistrationScreenToTrackerScreen() {
        Navigation.findNavController(this, R.id.map_nav_host_fragment).navigate(R.id.action_registrationFragment_to_trackerFragment)
    }

    override fun proceedRegistrationScreenToMapScreen() {
        Navigation.findNavController(this, R.id.map_nav_host_fragment).navigate(R.id.action_registrationFragment_to_mapFragment)
    }

    override fun proceedMapScreenToLoginScreen() {
        Navigation.findNavController(this, R.id.map_nav_host_fragment).navigate(R.id.action_mapFragment_to_loginFragment)
    }

    override fun showDatePickerDialog() {
        DatePickerFragment().show(supportFragmentManager, DatePickerFragment.TAG)
    }
}
