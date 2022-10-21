package playduin.locationtracker.ui.mvi

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData

class FragmentContract {
    interface ViewModel<T, S> : LifecycleObserver {
        fun getStateObservable(): LiveData<T>?
        fun getActionObservable(): LiveData<S>?
    }

    interface View
    interface Host
}
