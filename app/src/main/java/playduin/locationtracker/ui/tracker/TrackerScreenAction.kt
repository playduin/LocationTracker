package playduin.locationtracker.ui.tracker

import playduin.locationtracker.ui.mvi.AbstractAction

sealed class TrackerScreenAction : AbstractAction<TrackerContract.View>() {
    class StartTracker : TrackerScreenAction() {
        override fun handle(screen: TrackerContract.View) {
            screen.startTracker()
        }
    }

    class StopTracker : TrackerScreenAction() {
        override fun handle(screen: TrackerContract.View) {
            screen.stopTracker()
        }
    }

    class ShowLogoutAlertDialog : TrackerScreenAction() {
        override fun handle(screen: TrackerContract.View) {
            screen.showLogoutAlertDialog()
        }
    }

    class ShowLoginFragment : TrackerScreenAction() {
        override fun handle(screen: TrackerContract.View) {
            screen.showLoginFragment()
        }
    }

    class RequestPermission : TrackerScreenAction() {
        override fun handle(screen: TrackerContract.View) {
            screen.requestPermission()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        if (other::class != this::class) {
            return false
        }
        val trackerScreenAction = other as TrackerScreenAction
        return trackerScreenAction == this
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}
