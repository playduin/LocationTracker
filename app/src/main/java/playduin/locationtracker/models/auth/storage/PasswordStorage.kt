package playduin.locationtracker.models.auth.storage

interface PasswordStorage {
    fun hasToken(): Boolean
    fun putToken(token: String?)
    fun clearToken()
}
