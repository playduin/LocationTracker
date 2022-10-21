package playduin.locationtracker.bg.job

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import playduin.locationtracker.App
import playduin.locationtracker.models.locations.tracker.TrackerLocationsRepository

class UploadWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    private val repository: TrackerLocationsRepository =
        App.instance?.getAppModule()?.trackerRepository()!!

    override fun doWork(): Result {
        val result: Array<Result?> = arrayOfNulls(1)
        repository.syncRemote()
                .andThen(repository.clear())
            .subscribe({ result[0] = Result.success() }) { result[0] = Result.retry() }
            .dispose()
        return result[0]!!
    }
}
