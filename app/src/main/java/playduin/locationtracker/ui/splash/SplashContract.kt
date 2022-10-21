package playduin.locationtracker.ui.splash

import playduin.locationtracker.ui.mvi.FragmentContract

class SplashContract {
    interface ViewModel : FragmentContract.ViewModel<SplashScreenState, SplashScreenAction>
    interface View : FragmentContract.View {
        fun setLoginFragment()
        fun setTrackerFragment()
        fun setMapFragment()
    }

    interface Host : FragmentContract.Host {
        fun proceedSplashScreenToLoginScreen()
        fun proceedSplashScreenToTrackerScreen()
        fun proceedSplashScreenToMapScreen()
    }
}
