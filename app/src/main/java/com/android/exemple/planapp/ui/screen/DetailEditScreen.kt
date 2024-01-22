package com.android.exemple.planapp.ui.screen

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.exemple.planapp.R
import com.android.exemple.planapp.ui.viewmodels.DetailViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@Composable
fun DetailEditScreen(
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel(),
    detailId: Int
) {

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val dateFormat =
        DateTimeFormatter.ofPattern(stringResource(R.string.format_yyyy_mm_dd_e), Locale.JAPAN)
    val focusRequesterDate = remember { FocusRequester() }
    val focusRequesterStartTime = remember { FocusRequester() }
    val focusRequesterEndTime = remember { FocusRequester() }

    var launched by rememberSaveable { mutableStateOf(false) }
    if (launched.not()) {
        LaunchedEffect(Unit) {
            viewModel.event(DetailViewModel.Event.EditInit(detailId = detailId))
            launched = true
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.screen_detail_edit)) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, stringResource(R.string.desc_back))
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.event(
                            DetailViewModel.Event.OnUpdateDetailClicked(
                                uiState,
                                detailId
                            )
                        )
                    }) {
                        Icon(Icons.Filled.Add, stringResource(R.string.desc_update))
                    }
                }
            )
        },
    ) {
        Column {
            Row {
                Text(
                    modifier = Modifier
                        .background(
                            color = Color(0xffcccccc)
                        ),
                    text = stringResource(R.string.label_title),
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .background(
                            color = Color(0xffcccccc)
                        ),
                    text = stringResource(R.string.label_required),
                    color = Color.Red
                )
            }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(start = 5.dp, end = 5.dp),
                value = uiState.title,
                onValueChange = {
                    viewModel.event(DetailViewModel.Event.TitleChanged(it))
                },
                label = { Text(stringResource(R.string.label_detail_title)) },
                singleLine = true,
                isError = uiState.titleErrorMessage.isNotEmpty(),
                trailingIcon = {
                    if (uiState.titleErrorMessage.isEmpty()) return@OutlinedTextField
                    Icon(
                        Icons.Filled.Error,
                        stringResource(R.string.desc_error),
                        tint = MaterialTheme.colors.error
                    )
                },
            )
            if (uiState.titleErrorMessage.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.error_title),
                    color = MaterialTheme.colors.error
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .background(
                        color = Color(0xffcccccc)
                    ),
                text = stringResource(R.string.label_date),
            )
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .focusRequester(focusRequesterDate),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = if (uiState.date == null) stringResource(R.string.empty) else dateFormat.format(
                        uiState.date
                    ),
                    onValueChange = {},
                )
                Spacer(modifier = Modifier.width(15.dp))
                IconButton(
                    modifier = Modifier.width(30.dp),
                    onClick = {
                        showDatePicker(
                            context,
                            onDecideDate = { date ->
                                viewModel.event(DetailViewModel.Event.DateChanged(date))
                            },
                        )
                        focusRequesterDate.requestFocus()
                    }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = stringResource(R.string.desc_start_day)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .background(
                        color = Color(0xffcccccc)
                    ),
                text = stringResource(R.string.label_start_time),
            )
            Row(
                modifier = Modifier
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequesterStartTime),
                    value = uiState.startTime?.toString() ?: stringResource(R.string.empty),
                    onValueChange = {},
                    readOnly = true,
                    isError = uiState.timeErrorMessage.isNotEmpty(),
                    trailingIcon = {
                        if (uiState.timeErrorMessage.isEmpty()) return@OutlinedTextField
                        Icon(
                            Icons.Filled.Error,
                            stringResource(R.string.desc_error),
                            tint = MaterialTheme.colors.error
                        )
                    },
                )
                Spacer(modifier = Modifier.width(15.dp))
                IconButton(
                    modifier = Modifier.width(30.dp),
                    onClick = {
                        showTimePicker(
                            context,
                            onDecideTime = { time ->
                                viewModel.event(DetailViewModel.Event.StartTimeChanged(time))
                            }
                        )
                        focusRequesterStartTime.requestFocus()
                    }) {
                    Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = stringResource(R.string.desc_start_time)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .background(
                        color = Color(0xffcccccc)
                    ),
                text = stringResource(R.string.label_end_time),
            )
            Row(
                modifier = Modifier
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequesterEndTime),
                    value = uiState.endTime?.toString() ?: stringResource(R.string.empty),
                    onValueChange = {},
                    readOnly = true,
                    isError = uiState.timeErrorMessage.isNotEmpty(),
                    trailingIcon = {
                        if (uiState.timeErrorMessage.isEmpty()) return@OutlinedTextField
                        Icon(
                            Icons.Filled.Error,
                            stringResource(R.string.desc_error),
                            tint = MaterialTheme.colors.error
                        )
                    },
                )
                Spacer(modifier = Modifier.width(15.dp))
                IconButton(
                    modifier = Modifier.width(30.dp),
                    onClick = {
                        showTimePicker(
                            context,
                            onDecideTime = { time ->
                                viewModel.event(DetailViewModel.Event.EndTimeChanged(time))
                            }
                        )
                        focusRequesterEndTime.requestFocus()
                    }) {
                    Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = stringResource(R.string.desc_end_time)
                    )
                }
            }
            if (uiState.timeErrorMessage.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.error_time_validate),
                    color = MaterialTheme.colors.error
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .background(
                        color = Color(0xffcccccc)
                    ),
                text = stringResource(R.string.label_cost),
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(start = 5.dp, end = 5.dp),
                value = uiState.cost,
                onValueChange = {
                    viewModel.event(DetailViewModel.Event.CostChanged(it))
                },
                label = { Text(stringResource(R.string.label_cost)) }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .background(
                        color = Color(0xffcccccc)
                    ),
                text = stringResource(R.string.label_url),
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(start = 5.dp, end = 5.dp),
                value = uiState.url,
                onValueChange = {
                    viewModel.event(DetailViewModel.Event.UrlChanged(it))
                },
                label = { Text(stringResource(R.string.label_link)) }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .background(
                        color = Color(0xffcccccc)
                    ),
                text = stringResource(R.string.label_memo),
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(150.dp)
                    .padding(start = 5.dp, end = 5.dp),
                value = uiState.memo,
                onValueChange = {
                    viewModel.event(DetailViewModel.Event.MemoChanged(it))
                },
                label = { Text(stringResource(R.string.label_memo)) }
            )
        }
    }
}

private fun showDatePicker(
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

private fun showTimePicker(
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