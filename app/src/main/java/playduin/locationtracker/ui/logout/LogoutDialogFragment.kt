package playduin.locationtracker.ui.logout

import android.content.DialogInterface
import android.os.Bundle
import playduin.locationtracker.App
import playduin.locationtracker.R
import playduin.locationtracker.models.cache.Cache

class LogoutDialogFragment : androidx.fragment.app.DialogFragment() {
    private val cache: Cache = App.instance?.getAppModule()?.cache()!!

    override fun onCreateDialog(savedInstanceState: Bundle?): android.app.Dialog {
        return android.app.AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.logout_alert_message))
            .setPositiveButton(getString(R.string.logout_alert_destroy_locations)) { _: DialogInterface?, _: Int ->
                cache.logoutDialogState(
                    LogoutDialogState.CLEAR_LOCATIONS_STATE
                )
            }
            .setNegativeButton(getString(R.string.logout_alert_wait_connection)) { _: DialogInterface?, _: Int ->
                cache.logoutDialogState(
                    LogoutDialogState.WAIT_CONNECTION_STATE
                )
            }
            .create()
    }

    companion object {
        const val TAG = "LogoutDialogFragment"
    }
}
