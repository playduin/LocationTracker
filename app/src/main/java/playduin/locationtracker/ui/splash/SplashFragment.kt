package playduin.locationtracker.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import playduin.locationtracker.databinding.FragmentSplashBinding
import playduin.locationtracker.ui.Launcher
import playduin.locationtracker.ui.mvi.HostedFragment

class SplashFragment : HostedFragment<SplashContract.View, SplashScreenState, SplashScreenAction, SplashContract.ViewModel, SplashContract.Host>(), SplashContract.View {
    private var binding: FragmentSplashBinding? = null

    override fun createModel(): SplashContract.ViewModel {
        return ViewModelProvider(this, SplashViewModelFactory(requireArguments().getInt(Launcher.key)))
                .get(SplashViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): FrameLayout? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun setLoginFragment() {
        fragmentHost?.proceedSplashScreenToLoginScreen()
    }

    override fun setTrackerFragment() {
        fragmentHost?.proceedSplashScreenToTrackerScreen()
    }

    override fun setMapFragment() {
        fragmentHost?.proceedSplashScreenToMapScreen()
    }
}