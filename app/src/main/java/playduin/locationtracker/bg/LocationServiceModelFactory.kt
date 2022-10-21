package playduin.locationtracker.bg

import playduin.locationtracker.bg.di.DaggerLocationServiceModule
import playduin.locationtracker.bg.di.LocationServiceComponent

class LocationServiceModelFactory(private val component: LocationServiceComponent) {

    fun create(): LocationServiceModel {
        return DaggerLocationServiceModule.builder()
                .locationServiceComponent(component)
                .build()
                .createSLocationServiceModel()
    }
}
