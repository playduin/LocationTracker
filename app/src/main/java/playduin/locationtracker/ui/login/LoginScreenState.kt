package playduin.locationtracker.ui.login

import playduin.locationtracker.ui.mvi.ScreenState

sealed class LoginScreenState(val wrongLogin: Boolean, val wrongPassword: Boolean) : ScreenState<LoginContract.View>() {
    class ShowProgressBar: LoginScreenState(false, false) {
        override fun visit(screen: LoginContract.View) {
            screen.showProgressBar()
        }
    }

    class HideProgressBar: LoginScreenState(false, false) {
        override fun visit(screen: LoginContract.View) {
            screen.hideProgressBar()
        }
    }

    class SetForm(wrongLogin: Boolean, wrongPassword: Boolean): LoginScreenState(wrongLogin, wrongPassword) {
        override fun visit(screen: LoginContract.View) {
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
        val loginScreenState = other as LoginScreenState
        return loginScreenState.wrongLogin == wrongLogin && loginScreenState.wrongPassword == wrongPassword
    }

    override fun hashCode(): Int {
        var result = wrongLogin.hashCode()
        result = 31 * result + wrongPassword.hashCode()
        return result
    }
}
