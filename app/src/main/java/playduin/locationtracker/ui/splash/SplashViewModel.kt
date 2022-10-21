package playduin.locationtracker.ui.splash

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import playduin.locationtracker.models.auth.AuthRepo
import playduin.locationtracker.ui.Launcher
import playduin.locationtracker.ui.mvi.BaseViewModel
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val authRepo: AuthRepo, private val launcher: Int
) : BaseViewModel<SplashScreenState, SplashScreenAction>(), SplashContract.ViewModel {

    override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?) {
        super.onAny(owner, event)
        if (event == Lifecycle.Event.ON_START) {
            if (authRepo.hasToken()) {
                if (Launcher.TRACKER == launcher) {
                    setAction(SplashScreenAction.SetTrackerFragment())
                } else if (Launcher.MAP == launcher) {
                    setAction(SplashScreenAction.SetMapFragment())
                }
            } else {
                setAction(SplashScreenAction.SetLoginFragment())
            }
        }
    }
}
