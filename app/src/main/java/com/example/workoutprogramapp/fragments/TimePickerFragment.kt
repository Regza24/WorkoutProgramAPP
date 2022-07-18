package com.example.workoutprogramapp.fragments

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val ref = TimePickerDialog.OnTimeSetListener{ timePicker, hourOfDay, minute ->
            c.set(Calendar.HOUR_OF_DAY, hourOfDay)
            c.set(Calendar.MINUTE, minute)
        }
        return TimePickerDialog(activity, ref, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true)
    }

}