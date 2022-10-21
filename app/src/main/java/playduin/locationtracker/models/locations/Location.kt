package playduin.locationtracker.models.locations

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

@androidx.room.Entity
data class Location(
    @field:ColumnInfo(name = "latitude") var latitude: Double,
    @field:ColumnInfo(name = "longitude") var longitude: Double,
    @field:ColumnInfo(name = "time") var time: Long
) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
