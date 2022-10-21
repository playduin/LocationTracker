package playduin.locationtracker.ui.registration

import playduin.locationtracker.ui.mvi.AbstractAction

sealed class RegistrationScreenAction(val text: Int) : AbstractAction<RegistrationContract.View>() {
    class SetLoginFragment: RegistrationScreenAction(0) {
        override fun handle(screen: RegistrationContract.View) {
            screen.setLoginFragment()
        }
    }

    class SetTrackerFragment: RegistrationScreenAction(0) {
        override fun handle(screen: RegistrationContract.View) {
            screen.setTrackerFragment()
        }
    }

    class SetMapFragment: RegistrationScreenAction(0) {
        override fun handle(screen: RegistrationContract.View) {
            screen.setMapFragment()
        }
    }

    class ShowToast(text: Int): RegistrationScreenAction(text) {
        override fun handle(screen: RegistrationContract.View) {
            screen.showToast(text)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        if (other.javaClass != this.javaClass) {
            return false
        }
        val registrationScreenAction = other as RegistrationScreenAction
        return registrationScreenAction.text == text
    }

    override fun hashCode(): Int {
        return text
    }
}
