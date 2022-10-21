package playduin.locationtracker.bg.di

import dagger.Component
import playduin.locationtracker.bg.LocationServiceModel

@Component(dependencies = [LocationServiceComponent::class])
interface LocationServiceModule {
    fun createSLocationServiceModel(): LocationServiceModel
}
