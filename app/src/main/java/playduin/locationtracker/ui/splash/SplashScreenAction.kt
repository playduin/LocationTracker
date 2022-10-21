package playduin.locationtracker.ui.splash

import playduin.locationtracker.ui.mvi.AbstractAction

sealed class SplashScreenAction : AbstractAction<SplashContract.View>() {
    class SetLoginFragment : SplashScreenAction() {
        override fun handle(screen: SplashContract.View) {
            screen.setLoginFragment()
        }
    }

    class SetTrackerFragment : SplashScreenAction() {
        override fun handle(screen: SplashContract.View) {
            screen.setTrackerFragment()
        }
    }

    class SetMapFragment : SplashScreenAction() {
        override fun handle(screen: SplashContract.View) {
            screen.setMapFragment()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        if (other.javaClass != this.javaClass) {
            return false
        }
        val splashScreenAction = other as SplashScreenAction
        return splashScreenAction == this
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}
