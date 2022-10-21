package playduin.locationtracker.models.auth.storage

import com.orhanobut.hawk.Hawk

class PasswordStorageImpl(appContext: android.content.Context?) : PasswordStorage {

    init {
        Hawk.init(appContext).build()
    }

    override fun hasToken() = Hawk.contains("token")

    override fun putToken(token: String?) {
        Hawk.put("token", token)
    }

    override fun clearToken() {
        Hawk.delete("token")
    }
}
