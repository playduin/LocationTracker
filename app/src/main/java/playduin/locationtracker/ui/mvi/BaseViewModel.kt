package playduin.locationtracker.ui.mvi

import androidx.annotation.CallSuper
import androidx.lifecycle.*
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseViewModel<T, S> : ViewModel(), FragmentContract.ViewModel<T, S> {
    protected val disposables = CompositeDisposable()
    private val stateHolder = MutableLiveData<T>()
    private val actionHolder = MutableLiveData<S>()

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    protected open fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?) {
    }

    protected fun setState(state: T) {
        state?.let { stateHolder.value = it }
    }

    protected fun setAction(action: S) {
        action?.let { actionHolder.value = it }
    }

    override fun onCleared() {
        disposables.dispose()
    }

    override fun getStateObservable(): LiveData<T>? = stateHolder

    override fun getActionObservable(): LiveData<S>? = actionHolder
}
