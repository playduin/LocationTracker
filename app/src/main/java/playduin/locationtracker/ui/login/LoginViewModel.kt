package playduin.locationtracker.ui.login

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import playduin.locationtracker.models.auth.AuthRepo
import playduin.locationtracker.ui.Launcher
import playduin.locationtracker.ui.auth.BaseAuthViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val repo: AuthRepo, private val launcher: Int
) : BaseAuthViewModel<LoginScreenState, LoginScreenAction>(), LoginContract.ViewModel {

    private var launch: Job? = null

    override fun login(username: String, password: String) {
        launch = viewModelScope.launch {
            repo.signIn(username, password)
                    .flowOn(Dispatchers.IO)
                    .onStart { setState(LoginScreenState.ShowProgressBar()) }
                    .onCompletion { setState(LoginScreenState.HideProgressBar()) }
                    .catch { error -> setAction(LoginScreenAction.ShowToast(getErrorDescription(error))) }
                    .collect {
                        if (Launcher.TRACKER == launcher) {
                            setAction(LoginScreenAction.SetTrackerFragment())
                        } else if (Launcher.MAP == launcher) {
                            setAction(LoginScreenAction.SetMapFragment())
                        }
                    }
        }
    }

    override fun onRegistrationButtonClick() {
        setAction(LoginScreenAction.SetRegistrationFragment())
    }

    override fun loginDataChanged(username: String, password: String) {
        setState(
            LoginScreenState.SetForm(
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
