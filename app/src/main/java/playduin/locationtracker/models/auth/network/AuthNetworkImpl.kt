package playduin.locationtracker.models.auth.network

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthNetworkImpl : AuthNetwork {
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun createAccount(email: String, password: String): Flow<String> {
        return flow {
            emit(getToken(mAuth.createUserWithEmailAndPassword(email, password))!!)
        }
    }

    private fun getToken(task: Task<AuthResult>): String? {
        return Tasks.await(task).user?.getIdToken(true)?.let {
            Tasks.await(it)?.token
        }
    }

    override fun signIn(email: String, password: String): Flow<String> {
        return flow {
            emit(getToken(mAuth.signInWithEmailAndPassword(email, password))!!)
        }
    }

    override fun signOut(): Flow<Unit> {
        return flow { emit(mAuth.signOut()) }
    }
}
