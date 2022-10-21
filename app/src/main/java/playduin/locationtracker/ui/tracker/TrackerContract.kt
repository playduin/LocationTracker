package playduin.locationtracker.ui.tracker

import io.reactivex.rxjava3.core.Observable
import playduin.locationtracker.ui.mvi.FragmentContract

class TrackerContract {
    interface ViewModel : FragmentContract.ViewModel<TrackerScreenState, TrackerScreenAction> {
        fun permissionGrantSubscribe(permissionGrantObservable: Observable<String>)
        fun toggleTracker(permissionState: Int)
        fun logout()
    }

    interface View : FragmentContract.View {
        fun startTracker()
        fun stopTracker()
        fun showLogoutAlertDialog()
        fun setButtonText(text: Int)
        fun setText(text: Int)
        fun setLocationsCount(count: Int)
        fun setGPSState(state: Boolean)
        fun showLoginFragment()
        fun requestPermission()
    }

    interface Host : FragmentContract.Host {
        fun startTracker()
        fun stopTracker()
        fun showLogoutAlertDialog()
        fun proceedTrackerScreenToLoginScreen()
        fun requestPermissions()
        fun getPermissionGrantObservable(): Observable<String>
    }
}
