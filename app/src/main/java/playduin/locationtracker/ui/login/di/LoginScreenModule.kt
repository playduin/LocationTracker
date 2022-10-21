package playduin.locationtracker.ui.login.di

import dagger.Component
import playduin.locationtracker.ui.login.LoginViewModel

@Component(dependencies = [LoginScreenComponent::class])
interface LoginScreenModule {
    fun createLoginViewModel(): LoginViewModel?
}
