package playduin.locationtracker.ui.registration.di

import playduin.locationtracker.models.auth.AuthRepo

interface RegistrationScreenComponent {
    fun authRepo(): AuthRepo
    fun launcher(): Int
}
