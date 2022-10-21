package playduin.locationtracker.ui.login.di

import dagger.Provides
import playduin.locationtracker.models.auth.AuthRepo
import javax.inject.Inject

@dagger.Module
class LoginScreenComponentImpl @Inject constructor(
    private var authRepoValue: AuthRepo, private var launcherValue: Int
) : LoginScreenComponent {

    @Provides
    override fun authRepo() = authRepoValue

    @Provides
    override fun launcher() = launcherValue
}
