package com.android.exemple.planapp.ui.util

import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.widget.TimePicker
import java.time.LocalTime

object TimePickerUtil {

    fun showTimePicker(
        context: Context,
        onDecideTime: (LocalTime) -> Unit,
    ) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(
            context,
            { _: TimePicker, pickedHour: Int, pickedMinute: Int ->
                onDecideTime(
                    LocalTime.of(pickedHour, pickedMinute)
                )
            }, hour, minute, false
        ).show()
    }
}