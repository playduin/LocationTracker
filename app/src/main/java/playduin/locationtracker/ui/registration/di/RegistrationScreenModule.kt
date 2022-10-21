package playduin.locationtracker.ui.registration.di

import playduin.locationtracker.ui.registration.RegistrationViewModel

@dagger.Component(dependencies = [RegistrationScreenComponent::class])
interface RegistrationScreenModule {
    fun createRegistrationViewModel(): RegistrationViewModel?
}
