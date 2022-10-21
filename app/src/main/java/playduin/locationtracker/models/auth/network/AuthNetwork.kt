package playduin.locationtracker.models.auth.network

import kotlinx.coroutines.flow.Flow

interface AuthNetwork {
    fun createAccount(email: String, password: String): Flow<String>
    fun signIn(email: String, password: String): Flow<String>
    fun signOut(): Flow<Unit>
}
