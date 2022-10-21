package playduin.locationtracker.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import playduin.locationtracker.App

class MapViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            DaggerMapScreenComponent.builder()
                .appModule(App.instance!!.getAppModule())
                .build()
                .createMapModel() as T
        } else {
            throw java.lang.IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
