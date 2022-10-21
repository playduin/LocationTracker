package playduin.locationtracker.ui.registration

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import playduin.locationtracker.App
import playduin.locationtracker.R
import playduin.locationtracker.ui.Launcher
import playduin.locationtracker.ui.mvi.HostedFragment

class RegistrationFragment : HostedFragment<RegistrationContract.View, RegistrationScreenState, RegistrationScreenAction, RegistrationContract.ViewModel, RegistrationContract.Host>(), RegistrationContract.View, android.view.View.OnClickListener {
    private var usernameEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var registerButton: android.widget.Button? = null
    private var loginText: TextView? = null
    private var loadingProgressBar: ProgressBar? = null

    override fun createModel(): RegistrationContract.ViewModel {
        return ViewModelProvider(this, RegistrationViewModelFactory(arguments?.getInt(Launcher.key)))
                .get(RegistrationViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): android.view.View {
        return inflater.inflate(R.layout.registration_fragment, container, false)
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        usernameEditText = view.findViewById(R.id.username)
        passwordEditText = view.findViewById(R.id.password)
        registerButton = view.findViewById(R.id.register)
        loginText = view.findViewById(R.id.login)
        loadingProgressBar = view.findViewById(R.id.loading)
        val afterTextChangedListener: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                model?.loginDataChanged(getText(usernameEditText), getText(passwordEditText))
            }
        }
        usernameEditText?.addTextChangedListener(afterTextChangedListener)
        passwordEditText?.addTextChangedListener(afterTextChangedListener)
        passwordEditText?.setOnEditorActionListener { _: TextView?, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                model?.register(getText(usernameEditText), getText(passwordEditText))
            }
            false
        }
        registerButton?.setOnClickListener(this)
        loginText?.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.login, menu)
    }

    override fun onClick(view: android.view.View) {
        if (view === registerButton) {
            model?.register(getText(usernameEditText), getText(passwordEditText))
        } else if (view === loginText) {
            model?.onLoginButtonClick()
        }
    }

    private fun getText(editText: EditText?): String {
        return editText?.text.toString().trim { it <= ' ' }
    }

    override fun setLoginFragment() {
        fragmentHost?.proceedRegistrationScreenToLoginScreen()
    }

    override fun setTrackerFragment() {
        fragmentHost?.proceedRegistrationScreenToTrackerScreen()
    }

    override fun setMapFragment() {
        fragmentHost?.proceedRegistrationScreenToMapScreen()
    }

    override fun showProgressBar() {
        loadingProgressBar?.visibility = android.view.View.VISIBLE
    }

    override fun hideProgressBar() {
        loadingProgressBar?.visibility = android.view.View.GONE
    }

    override fun showToast(text: Int) {
        App.showToast(text)
    }

    override fun setForm(wrongLogin: Boolean, wrongPassword: Boolean) {
        registerButton?.isEnabled = !wrongLogin && !wrongPassword
        if (wrongLogin) {
            usernameEditText?.error = getString(R.string.invalid_username)
        }
        if (wrongPassword) {
            passwordEditText?.error = getString(R.string.invalid_password)
        }
    }
}
