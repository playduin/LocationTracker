package playduin.locationtracker.ui.mvi

abstract class AbstractAction<T : FragmentContract.View?> : ScreenAction<T> {
    private var isHandled = false

    override fun visit(screen: T) {
        if (!isHandled) {
            handle(screen)
            isHandled = true
        }
    }

    protected abstract fun handle(screen: T)
}
