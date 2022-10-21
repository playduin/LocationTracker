package playduin.locationtracker.ui.map

import playduin.locationtracker.models.AppModule

@dagger.Component(dependencies = [AppModule::class])
interface MapScreenComponent {
    fun createMapModel(): MapViewModel
}
