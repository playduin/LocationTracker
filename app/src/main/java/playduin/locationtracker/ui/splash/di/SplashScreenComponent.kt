package playduin.locationtracker.ui.splash.di

import playduin.locationtracker.models.auth.AuthRepo

interface SplashScreenComponent {
    fun authRepo(): AuthRepo
    fun launcher(): Int
}
