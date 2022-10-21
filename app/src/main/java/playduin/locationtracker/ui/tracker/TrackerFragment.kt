package playduin.locationtracker.ui.tracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.PermissionChecker
import androidx.lifecycle.ViewModelProvider
import playduin.locationtracker.R
import playduin.locationtracker.databinding.TrackerFragmentBinding
import playduin.locationtracker.ui.mvi.HostedFragment

class TrackerFragment : HostedFragment<TrackerContract.View, TrackerScreenState, TrackerScreenAction, TrackerContract.ViewModel, TrackerContract.Host>(), TrackerContract.View, android.view.View.OnClickListener {
    private var binding: TrackerFragmentBinding? = null

    override fun createModel(): TrackerContract.ViewModel {
        return ViewModelProvider(this, TrackerViewModelFactory())
                .get(TrackerViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): ConstraintLayout? {
        binding = TrackerFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        binding?.logout?.setOnClickListener(this)
        binding?.toggleTracker?.setOnClickListener(this)
        fragmentHost?.let {
            model?.permissionGrantSubscribe(it.getPermissionGrantObservable().hide())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun startTracker() {
        fragmentHost?.startTracker()
    }

    override fun stopTracker() {
        fragmentHost?.stopTracker()
    }

    override fun setButtonText(text: Int) {
        binding?.toggleTracker?.setText(text)
    }

    override fun setText(text: Int) {
        binding?.statusText?.setText(text)
    }

    override fun setLocationsCount(count: Int) {
        binding?.locationsCountText?.text = getString(R.string.locations_count, count)
    }

    override fun setGPSState(state: Boolean) {
        binding?.gpsStateText?.text = getString(
            R.string.gps_state,
            if (state) getString(R.string.on) else getString(R.string.off)
        )
    }

    override fun showLoginFragment() {
        fragmentHost?.proceedTrackerScreenToLoginScreen()
    }

    override fun requestPermission() {
        fragmentHost?.requestPermissions()
    }

    override fun showLogoutAlertDialog() {
        fragmentHost?.showLogoutAlertDialog()
    }

    override fun onClick(view: android.view.View) {
        if (view ===  binding?.logout) {
            model?.logout()
        } else if (view ===  binding?.toggleTracker) {
            model?.toggleTracker(
                PermissionChecker.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        }
    }
}
