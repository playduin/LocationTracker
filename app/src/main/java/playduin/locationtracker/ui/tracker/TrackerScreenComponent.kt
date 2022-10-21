package playduin.locationtracker.ui.tracker

import playduin.locationtracker.models.AppModule

@dagger.Component(dependencies = [AppModule::class])
interface TrackerScreenComponent {
    fun createTrackerModel(): TrackerViewModel?
}
