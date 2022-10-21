package playduin.locationtracker.ui.mvi

interface ScreenAction<S : FragmentContract.View?> {
    fun visit(screen: S)
}
