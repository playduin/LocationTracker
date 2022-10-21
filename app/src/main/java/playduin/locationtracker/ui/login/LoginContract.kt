package playduin.locationtracker.ui.login

import playduin.locationtracker.ui.mvi.FragmentContract

class LoginContract {

    interface ViewModel : FragmentContract.ViewModel<LoginScreenState, LoginScreenAction> {
        fun login(username: String, password: String)
        fun onRegistrationButtonClick()
        fun loginDataChanged(username: String, password: String)
    }

    interface View : FragmentContract.View {
        fun setRegistrationFragment()
        fun setTrackerFragment()
        fun setMapFragment()
        fun showProgressBar()
        fun hideProgressBar()
        fun showToast(text: Int)
        fun setForm(wrongLogin: Boolean, wrongPassword: Boolean)
    }

    interface Host : FragmentContract.Host {
        fun proceedLoginScreenToRegistrationScreen()
        fun proceedLoginScreenToTrackerScreen()
        fun proceedLoginScreenToMapScreen()
    }
}
