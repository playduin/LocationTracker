package playduin.locationtracker.ui.registration

import playduin.locationtracker.ui.mvi.FragmentContract

class RegistrationContract {

    interface ViewModel : FragmentContract.ViewModel<RegistrationScreenState, RegistrationScreenAction> {
        fun register(username: String, password: String)
        fun onLoginButtonClick()
        fun loginDataChanged(username: String, password: String)
    }

    interface View : FragmentContract.View {
        fun setLoginFragment()
        fun setTrackerFragment()
        fun setMapFragment()
        fun showProgressBar()
        fun hideProgressBar()
        fun showToast(text: Int)
        fun setForm(wrongLogin: Boolean, wrongPassword: Boolean)
    }

    interface Host : FragmentContract.Host {
        fun proceedRegistrationScreenToLoginScreen()
        fun proceedRegistrationScreenToTrackerScreen()
        fun proceedRegistrationScreenToMapScreen()
    }
}
