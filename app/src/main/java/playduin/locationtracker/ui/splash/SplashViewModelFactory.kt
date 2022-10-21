package playduin.locationtracker.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import playduin.locationtracker.App
import playduin.locationtracker.ui.splash.di.DaggerSplashScreenModule
import playduin.locationtracker.ui.splash.di.SplashScreenComponent
import playduin.locationtracker.ui.splash.di.SplashScreenComponentImpl

class SplashViewModelFactory(launcher: Int) : ViewModelProvider.Factory {
    private val splashScreenComponent: SplashScreenComponent

    init {
        splashScreenComponent =
            SplashScreenComponentImpl(App.instance?.getAppModule()?.authRepo()!!, launcher)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            DaggerSplashScreenModule.builder()
                .splashScreenComponent(splashScreenComponent)
                .build()
                .createSplashViewModel() as T
        } else {
            throw java.lang.IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
