package playduin.locationtracker.ui.login.di

import playduin.locationtracker.models.auth.AuthRepo

interface LoginScreenComponent {
    fun authRepo(): AuthRepo
    fun launcher(): Int
}
