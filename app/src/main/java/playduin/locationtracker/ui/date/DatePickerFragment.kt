package playduin.locationtracker.ui.date

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import playduin.locationtracker.App
import playduin.locationtracker.models.cache.Cache
import java.text.SimpleDateFormat

class DatePickerFragment : androidx.fragment.app.DialogFragment(), DatePickerDialog.OnDateSetListener {
    private val cache: Cache = App.instance?.getAppModule()?.cache()!!

    override fun onCreateDialog(savedInstanceState: Bundle?): android.app.Dialog {
        val c = java.util.Calendar.getInstance()
        val year = c[java.util.Calendar.YEAR]
        val month = c[java.util.Calendar.MONTH]
        val day = c[java.util.Calendar.DAY_OF_MONTH]
        return DatePickerDialog(requireActivity(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        cache.datePickerState(milliseconds(year.toString() + "-" + (month + 1) + "-" + day))
    }

    private fun milliseconds(date: String): Long {
        try {
            return SimpleDateFormat("yyyy-MM-dd").parse(date)!!.time
        } catch (e: java.text.ParseException) {
            e.printStackTrace()
        }
        return 0
    }

    companion object {
        const val TAG = "DatePickerFragment"
    }
}
