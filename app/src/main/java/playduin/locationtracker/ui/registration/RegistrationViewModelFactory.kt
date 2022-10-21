package playduin.locationtracker.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import playduin.locationtracker.App
import playduin.locationtracker.ui.registration.di.DaggerRegistrationScreenModule
import playduin.locationtracker.ui.registration.di.RegistrationScreenComponent
import playduin.locationtracker.ui.registration.di.RegistrationScreenComponentImpl

class RegistrationViewModelFactory(launcher: Int?) : ViewModelProvider.Factory {
    private val registrationScreenComponent: RegistrationScreenComponent

    init {
        registrationScreenComponent =
            RegistrationScreenComponentImpl(App.instance?.getAppModule()?.authRepo()!!, launcher!!)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            DaggerRegistrationScreenModule.builder()
                .registrationScreenComponent(registrationScreenComponent)
                .build()
                .createRegistrationViewModel() as T
        } else {
            throw java.lang.IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
