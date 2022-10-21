package playduin.locationtracker.ui.auth

import android.util.Patterns
import playduin.locationtracker.R
import playduin.locationtracker.ui.mvi.BaseViewModel

open class BaseAuthViewModel<T, S> : BaseViewModel<T, S>() {
    protected fun getErrorDescription(error: Throwable): Int {
        return when {
            error.message?.startsWith("com.google.firebase.FirebaseNetworkException") == true -> {
                R.string.network_error
            }
            error.message?.startsWith("com.google.firebase.auth.FirebaseAuthUserCollisionException") == true -> {
                R.string.register_failed
            }
            error.message?.startsWith("com.google.firebase.auth.FirebaseAuthInvalidUserException") == true -> {
                R.string.login_username_wrong
            }
            error.message?.startsWith("com.google.firebase.auth.FirebaseAuthInvalidCredentialsException") == true -> {
                R.string.login_password_wrong
            }
            else -> R.string.unknown_error
        }
    }

    protected fun isUserNameNotValid(username: String): Boolean {
        return !Patterns.EMAIL_ADDRESS.matcher(username).matches()
    }

    protected fun isPasswordNotValid(password: String): Boolean {
        return password.trim { it <= ' ' }.length <= 5
    }
}
