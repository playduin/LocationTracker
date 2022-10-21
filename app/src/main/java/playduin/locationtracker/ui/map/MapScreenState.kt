package playduin.locationtracker.ui.map

import com.google.android.gms.maps.model.LatLng
import playduin.locationtracker.models.locations.Marker
import playduin.locationtracker.ui.mvi.ScreenState

sealed class MapScreenState(val pos: LatLng?, val markers: List<Marker>) : ScreenState<MapContract.View>() {
    class AddMarkers(markers: List<Marker>) : MapScreenState(null, markers) {
        override fun visit(screen: MapContract.View) {
            screen.mapAddMarkers(markers)
        }
    }

    class MoveCamera(pos: LatLng?) : MapScreenState(pos, listOf()) {
        override fun visit(screen: MapContract.View) {
            screen.mapMoveCamera(pos)
        }
    }

    class ClearMap : MapScreenState(null, listOf()) {
        override fun visit(screen: MapContract.View) {
            screen.mapClear()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        if (other.javaClass != this.javaClass) {
            return false
        }
        val mapScreenState = other as MapScreenState
        return (pos == null || mapScreenState.pos == pos) && mapScreenState.markers == markers
    }

    override fun hashCode(): Int {
        var result = pos?.hashCode() ?: 0
        result = 31 * result + markers.hashCode()
        return result
    }
}
