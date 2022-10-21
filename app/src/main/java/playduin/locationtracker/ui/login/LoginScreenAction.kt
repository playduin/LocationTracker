package playduin.locationtracker.ui.login

import playduin.locationtracker.ui.mvi.AbstractAction

sealed class LoginScreenAction(val text: Int) : AbstractAction<LoginContract.View>() {
    class SetRegistrationFragment: LoginScreenAction(0) {
        override fun handle(screen: LoginContract.View) {
            screen.setRegistrationFragment()
        }
    }

    class SetTrackerFragment: LoginScreenAction(0) {
        override fun handle(screen: LoginContract.View) {
            screen.setTrackerFragment()
        }
    }

    class SetMapFragment: LoginScreenAction(0) {
        override fun handle(screen: LoginContract.View) {
            screen.setMapFragment()
        }
    }

    class ShowToast(text: Int): LoginScreenAction(text) {
        override fun handle(screen: LoginContract.View) {
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
        val loginScreenAction = other as LoginScreenAction
        return loginScreenAction.text == text
    }

    override fun hashCode(): Int {
        return text
    }
}
