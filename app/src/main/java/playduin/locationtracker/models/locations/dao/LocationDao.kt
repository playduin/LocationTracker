package playduin.locationtracker.models.locations.dao

import androidx.room.Dao
import androidx.room.Query
import playduin.locationtracker.models.locations.Location

@Dao
interface LocationDao {
    @Query("SELECT * FROM location")
    fun getAll(): List<Location>

    @Query("SELECT *, MAX(time) FROM location")
    fun getLastLocation(): List<Location>

    @androidx.room.Query("SELECT * FROM location WHERE time>=:fromTime AND time<:endTime")
    fun getLocationsByTime(fromTime: Long, endTime: Long): List<Location>

    @androidx.room.Insert
    @androidx.room.Transaction
    fun insert(location: Location?)

    @androidx.room.Insert
    @androidx.room.Transaction
    fun insert(locations: List<Location>)

    @androidx.room.Transaction
    @androidx.room.Query("delete from location")
    fun deleteAll()
}
