package playduin.locationtracker.ui.registration.di

import dagger.Provides
import playduin.locationtracker.models.auth.AuthRepo
import javax.inject.Inject

@dagger.Module
class RegistrationScreenComponentImpl @Inject constructor(
    private var authRepoValue: AuthRepo, private var launcherValue: Int
) : RegistrationScreenComponent {

    @Provides
    override fun authRepo() = authRepoValue

    @Provides
    override fun launcher() = launcherValue
}
