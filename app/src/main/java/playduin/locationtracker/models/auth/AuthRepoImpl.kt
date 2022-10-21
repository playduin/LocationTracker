package playduin.locationtracker.models.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import playduin.locationtracker.models.auth.network.AuthNetwork
import playduin.locationtracker.models.auth.storage.PasswordStorage

class AuthRepoImpl(
    private val passwordStorage: PasswordStorage, private val authNetwork: AuthNetwork
) : AuthRepo {

    override fun hasToken() = passwordStorage.hasToken()

    override fun createAccount(email: String, password: String): Flow<String> {
        return authNetwork.createAccount(email, password).onEach (passwordStorage::putToken)
    }

    override fun signIn(email: String, password: String): Flow<String> {
        return authNetwork.signIn(email, password).onEach (passwordStorage::putToken)
    }

    override fun signOut(): Flow<Unit> {
        return authNetwork.signOut().onEach { passwordStorage.clearToken() }
    }
}