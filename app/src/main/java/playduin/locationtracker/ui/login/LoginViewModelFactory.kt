package playduin.locationtracker.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import playduin.locationtracker.App
import playduin.locationtracker.ui.login.di.DaggerLoginScreenModule
import playduin.locationtracker.ui.login.di.LoginScreenComponent
import playduin.locationtracker.ui.login.di.LoginScreenComponentImpl

class LoginViewModelFactory(launcher: Int?) : ViewModelProvider.Factory {
    private val loginScreenComponent: LoginScreenComponent

    init {
        loginScreenComponent =
            LoginScreenComponentImpl(App.instance?.getAppModule()?.authRepo()!!, launcher!!)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return DaggerLoginScreenModule.builder()
                .loginScreenComponent(loginScreenComponent)
                .build()
                .createLoginViewModel() as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
