package playduin.locationtracker.ui.mvi

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment

abstract class HostedFragment<VIEW : FragmentContract.View, STATE : ScreenState<VIEW>,
        ACTION : ScreenAction<VIEW>, VIEW_MODEL : FragmentContract.ViewModel<STATE, ACTION>,
        HOST : FragmentContract.Host> : NavHostFragment(), FragmentContract.View {

    protected var model: VIEW_MODEL? = null
        private set
    protected var fragmentHost: HOST? = null
        private set

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        // keep the call back
        fragmentHost = try {
            context as HOST
        } catch (e: Throwable) {
            val hostClassName = ((javaClass.genericSuperclass as java.lang.reflect.ParameterizedType)
                    .actualTypeArguments[1] as Class<*>).canonicalName
            throw java.lang.RuntimeException("Activity must implement " + hostClassName
                    + " to attach " + this.javaClass.simpleName, e)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setModel(createModel())
        model?.let {
            lifecycle.addObserver(it)
            it.getStateObservable()
                ?.observe(this) { screenState -> screenState.visit(this as VIEW) }
            it.getActionObservable()
                ?.observe(this) { screenAction -> screenAction.visit(this as VIEW) }
        }
    }

    protected abstract fun createModel(): VIEW_MODEL
    override fun onDetach() {
        super.onDetach()
        // release the call back
        fragmentHost = null
    }

    override fun onDestroy() {
        // order matters
        model?.let {
            it.getStateObservable()?.removeObservers(this)
            it.getActionObservable()?.removeObservers(this)
            lifecycle.removeObserver(it)
        }
        super.onDestroy()
    }

    private fun setModel(model: VIEW_MODEL) {
        this.model = model
    }
}