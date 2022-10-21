package playduin.locationtracker.ui.map

import playduin.locationtracker.ui.mvi.AbstractAction

sealed class MapScreenAction : AbstractAction<MapContract.View>() {
    class ShowLoginFragment : MapScreenAction() {
        override fun handle(screen: MapContract.View) {
            screen.showLoginFragment()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        if (other.javaClass != this.javaClass) {
            return false
        }
        val mapScreenAction = other as MapScreenAction
        return mapScreenAction == this
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}
