package playduin.locationtracker.ui.login

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

class LoginFragment : HostedFragment<LoginContract.View, LoginScreenState, LoginScreenAction, LoginContract.ViewModel, LoginContract.Host>(), LoginContract.View, android.view.View.OnClickListener {
    private var usernameEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var loginButton: android.widget.Button? = null
    private var registerText: TextView? = null
    private var loadingProgressBar: ProgressBar? = null

    override fun createModel(): LoginContract.ViewModel {
        return ViewModelProvider(this, LoginViewModelFactory(arguments?.getInt(Launcher.key)))
                .get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): android.view.View {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        usernameEditText = view.findViewById(R.id.username)
        passwordEditText = view.findViewById(R.id.password)
        loginButton = view.findViewById(R.id.login)
        registerText = view.findViewById(R.id.register)
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
                model?.login(getText(usernameEditText), getText(passwordEditText))
            }
            false
        }
        loginButton?.setOnClickListener(this)
        registerText?.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.login, menu)
    }

    override fun onClick(view: android.view.View) {
        if (view === loginButton) {
            model?.login(getText(usernameEditText), getText(passwordEditText))
        } else if (view === registerText) {
            model?.onRegistrationButtonClick()
        }
    }

    private fun getText(editText: EditText?): String {
        return editText?.text.toString().trim { it <= ' ' }
    }

    override fun setRegistrationFragment() {
        fragmentHost?.proceedLoginScreenToRegistrationScreen()
    }

    override fun setTrackerFragment() {
        fragmentHost?.proceedLoginScreenToTrackerScreen()
    }

    override fun setMapFragment() {
        fragmentHost?.proceedLoginScreenToMapScreen()
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
        loginButton!!.isEnabled = !wrongLogin && !wrongPassword
        if (wrongLogin) {
            usernameEditText?.error = getString(R.string.invalid_username)
        }
        if (wrongPassword) {
            passwordEditText?.error = getString(R.string.invalid_password)
        }
    }
}
