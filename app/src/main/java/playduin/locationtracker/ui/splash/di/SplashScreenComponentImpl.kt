package playduin.locationtracker.ui.splash.di

import dagger.Provides
import playduin.locationtracker.models.auth.AuthRepo
import javax.inject.Inject

@dagger.Module
class SplashScreenComponentImpl @Inject constructor(
    private var authRepoValue: AuthRepo, private var launcherValue: Int
) : SplashScreenComponent {

    @Provides
    override fun authRepo() = authRepoValue

    @Provides
    override fun launcher() = launcherValue
}
