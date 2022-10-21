package playduin.locationtracker.ui.splash.di

import playduin.locationtracker.ui.splash.SplashViewModel

@dagger.Component(dependencies = [SplashScreenComponent::class])
interface SplashScreenModule {
    fun createSplashViewModel(): SplashViewModel?
}
