package playduin.locationtracker.ui.tracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import playduin.locationtracker.App

class TrackerViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(TrackerViewModel::class.java)) {
            DaggerTrackerScreenComponent.builder()
                .appModule(App.instance?.getAppModule())
                .build()
                .createTrackerModel() as T
        } else {
            throw java.lang.IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
