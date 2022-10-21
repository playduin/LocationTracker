package playduin.locationtracker.models.locations.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import playduin.locationtracker.models.locations.Location

@Database(entities = [Location::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao?
}
