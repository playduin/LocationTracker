package playduin.locationtracker.ui.registration

import playduin.locationtracker.ui.mvi.ScreenState

sealed class RegistrationScreenState(val wrongLogin: Boolean, val wrongPassword: Boolean) : ScreenState<RegistrationContract.View>() {
    class ShowProgressBar: RegistrationScreenState(false, false) {
        override fun visit(screen: RegistrationContract.View) {
            screen.showProgressBar()
        }
    }

    class HideProgressBar: RegistrationScreenState(false, false) {
        override fun visit(screen: RegistrationContract.View) {
            screen.hideProgressBar()
        }
    }

    class SetForm(wrongLogin: Boolean, wrongPassword: Boolean): RegistrationScreenState(wrongLogin, wrongPassword) {
        override fun visit(screen: RegistrationContract.View) {
            screen.setForm(wrongLogin, wrongPassword)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        if (other.javaClass != this.javaClass) {
            return false
        }
        val registrationScreenState = other as RegistrationScreenState
        return registrationScreenState.wrongLogin == wrongLogin && registrationScreenState.wrongPassword == wrongPassword
    }

    override fun hashCode(): Int {
        var result = wrongLogin.hashCode()
        result = 31 * result + wrongPassword.hashCode()
        return result
    }
}
