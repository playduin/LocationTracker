package playduin.locationtracker.ui.registration

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import playduin.locationtracker.models.auth.AuthRepo
import playduin.locationtracker.ui.Launcher
import playduin.locationtracker.ui.auth.BaseAuthViewModel
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val repo: AuthRepo, private val launcher: Int
) : BaseAuthViewModel<RegistrationScreenState, RegistrationScreenAction>(),
    RegistrationContract.ViewModel {

    private var launch: Job? = null

    override fun register(username: String, password: String) {
        launch = viewModelScope.launch {
            repo.createAccount(username, password)
                    .flowOn(Dispatchers.IO)
                    .onStart { setState(RegistrationScreenState.ShowProgressBar()) }
                    .onCompletion { setState(RegistrationScreenState.HideProgressBar()) }
                    .catch { error -> setAction(RegistrationScreenAction.ShowToast(getErrorDescription(error))) }
                    .collect {
                        if (Launcher.TRACKER == launcher) {
                            setAction(RegistrationScreenAction.SetTrackerFragment())
                        } else if (Launcher.MAP == launcher) {
                            setAction(RegistrationScreenAction.SetMapFragment())
                        }
                    }
        }
    }

    override fun onLoginButtonClick() {
        setAction(RegistrationScreenAction.SetLoginFragment())
    }

    override fun loginDataChanged(username: String, password: String) {
        setState(
            RegistrationScreenState.SetForm(
                isUserNameNotValid(username),
                isPasswordNotValid(password)
            )
        )
    }

    override fun onCleared() {
        super.onCleared()
        launch?.cancel()
    }
}
