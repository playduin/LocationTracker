package playduin.locationtracker

import android.app.Application
import android.widget.Toast
import playduin.locationtracker.models.AppModule
import playduin.locationtracker.models.DefaultAppModule

class App : Application() {
    private var appModule: AppModule? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        appModule = DefaultAppModule(instance!!)
    }

    fun getAppModule() = appModule

    fun setAppModule(appModule: AppModule?) {
        this.appModule = appModule
    }

    companion object {
        var instance: App? = null
            private set

        fun showToast(text: Int) {
            Toast.makeText(instance!!.applicationContext, text, Toast.LENGTH_SHORT).show()
        }
    }
}
