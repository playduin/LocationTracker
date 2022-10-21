package playduin.locationtracker.bg

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import io.reactivex.rxjava3.disposables.Disposable
import playduin.locationtracker.App
import playduin.locationtracker.R
import playduin.locationtracker.bg.di.LocationServiceComponentImpl
import playduin.locationtracker.bg.job.UploadWorker
import playduin.locationtracker.ui.TrackerActivity

class LocationService : Service(), LocationsJobScheduler {
    private val locationServiceModel: LocationServiceModel = LocationServiceModelFactory(LocationServiceComponentImpl(App.instance?.getAppModule()!!, this)).create()
    private var disposable: Disposable? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (ACTION_STOP_SERVICE == intent.action) {
            stopSelf()
            val it = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
            sendBroadcast(it)
        } else {
            locationServiceModel.start()
            createNotification(false)

            disposable = locationServiceModel.getLocationAvailabilityObservable().subscribe(
                { gpsState -> createNotification(gpsState) }
            ) { it.printStackTrace() }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        locationServiceModel.stop()
        disposable?.dispose()
    }

    private fun createNotification(gpsState: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager: NotificationManager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
        val notificationIntent = Intent(this, TrackerActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0)
        val stopSelf = Intent(this, LocationService::class.java)
        stopSelf.action = ACTION_STOP_SERVICE
        val pStopSelf: PendingIntent = PendingIntent.getService(this, 0, stopSelf, PendingIntent.FLAG_CANCEL_CURRENT)
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getString(R.string.gps_tracker))
                .setContentText(getString(R.string.notification_text, if (gpsState) getString(R.string.on) else getString(R.string.off)))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .addAction(R.mipmap.ic_launcher, getString(R.string.stop), pStopSelf)
                .build()
        startForeground(1, notification)
    }

    override fun scheduleSendJob() {
        val workManager: WorkManager = WorkManager.getInstance(this)
        val work: OneTimeWorkRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
                .setConstraints(Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build())
                .build()
        workManager.enqueue(work)
    }

    companion object {
        const val CHANNEL_ID = "playduin.locationtracker.GPSTracker.ForegroundServiceChannel"
        const val CHANNEL_NAME = "Tracker Foreground Service Channel"
        private const val ACTION_STOP_SERVICE = "STOP_SERVICE"

        fun newIntent(ctx: Context?): Intent {
            return Intent(ctx, LocationService::class.java)
        }
    }
}
