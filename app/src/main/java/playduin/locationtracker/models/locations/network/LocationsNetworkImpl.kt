package playduin.locationtracker.models.locations.network

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import playduin.locationtracker.models.locations.Location

class LocationsNetworkImpl : LocationsNetwork {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun getLocations(startTime: Long): Single<List<Location>> {
        return Single.fromCallable {
            val user: FirebaseUser = auth.currentUser!!
            val task: Task<QuerySnapshot> = db.collection("location")
                .whereEqualTo("user", user.uid)
                .orderBy("time")
                .whereGreaterThan("time", startTime)
                .get()

            return@fromCallable Tasks.await(task).documents.map {
                Location(
                    it.getDouble("latitude")!!,
                    it.getDouble("longitude")!!,
                    it.getLong("time")!!
                )
            }
        }
    }

    override fun sendLocation(locations: List<Location>): Completable {
        return Completable.fromAction {
            val uid: String? = auth.currentUser?.uid
            for (l in locations.subList(0, 2)) {
                val data = mutableMapOf<String, Any>()
                data["latitude"] = l.latitude
                data["longitude"] = l.longitude
                data["time"] = l.time
                data["user"] = uid!!
                Tasks.await(db.collection("location").add(data))
            }
        }
    }
}
