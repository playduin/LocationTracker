package playduin.locationtracker.ui.splash

import playduin.locationtracker.ui.mvi.ScreenState

sealed class SplashScreenState : ScreenState<SplashContract.View>() {
    override fun visit(screen: SplashContract.View) {}
}
