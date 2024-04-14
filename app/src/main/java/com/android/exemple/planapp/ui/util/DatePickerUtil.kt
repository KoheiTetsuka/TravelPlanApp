package com.android.exemple.planapp.ui.util

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

object DatePickerUtil {

    fun showDatePicker(
        context: Context,
        onDecideDate: (LocalDate) -> Unit,
    ) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        calendar.time = Date()

        DatePickerDialog(
            context,
            { _: DatePicker, pickedYear: Int, pickedMonth: Int, pickedDay: Int ->
                onDecideDate(
                    LocalDate.of(pickedYear, pickedMonth + 1, pickedDay)
                )
            }, year, month, day
        ).show()
    }
}