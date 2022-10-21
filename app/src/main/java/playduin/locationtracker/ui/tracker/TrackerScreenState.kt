package playduin.locationtracker.ui.tracker

import playduin.locationtracker.ui.mvi.ScreenState

sealed class TrackerScreenState(val buttonText: Int, val text: Int, val count: Int, val state: Boolean) : ScreenState<TrackerContract.View>() {
    class ServiceRunning(buttonText: Int, text: Int) : TrackerScreenState(buttonText, text, 0, false) {
        override fun visit(screen: TrackerContract.View) {
            screen.setButtonText(buttonText)
            screen.setText(text)
        }
    }

    class ServiceStopped(buttonText: Int, text: Int) : TrackerScreenState(buttonText, text, 0, false) {
        override fun visit(screen: TrackerContract.View) {
            screen.setButtonText(buttonText)
            screen.setText(text)
        }
    }

    class SetLocationsCount(count: Int) : TrackerScreenState(0, 0, count, false) {
        override fun visit(screen: TrackerContract.View) {
            screen.setLocationsCount(count)
        }
    }

    class SetGPSState(state: Boolean) : TrackerScreenState(0, 0, 0, state) {
        override fun visit(screen: TrackerContract.View) {
            screen.setGPSState(state)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        if (other.javaClass != this.javaClass) {
            return false
        }
        val trackerScreenState = other as TrackerScreenState
        return trackerScreenState.buttonText == buttonText && trackerScreenState.text == text && trackerScreenState.count == count && trackerScreenState.state == state
    }

    override fun hashCode(): Int {
        var result = buttonText
        result = 31 * result + text
        result = 31 * result + count
        result = 31 * result + state.hashCode()
        return result
    }
}
