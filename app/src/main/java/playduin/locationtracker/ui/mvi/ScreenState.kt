package playduin.locationtracker.ui.mvi

abstract class ScreenState<T : FragmentContract.View?> {
    abstract fun visit(screen: T)
}
