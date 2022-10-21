package playduin.locationtracker.models.auth

import kotlinx.coroutines.flow.Flow

interface AuthRepo {
    fun hasToken(): Boolean
    fun createAccount(email: String, password: String): Flow<String>
    fun signIn(email: String, password: String): Flow<String>
    fun signOut(): Flow<Unit>
}
