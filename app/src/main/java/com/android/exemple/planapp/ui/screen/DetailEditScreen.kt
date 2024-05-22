package com.android.exemple.planapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.exemple.planapp.R
import com.android.exemple.planapp.ui.theme.LocalThemeColors
import com.android.exemple.planapp.ui.util.DatePickerUtil
import com.android.exemple.planapp.ui.util.TimePickerUtil
import com.android.exemple.planapp.ui.viewmodels.DetailViewModel
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DetailEditScreen(
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel(),
    detailId: Int,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val themeColors = LocalThemeColors.current

    val uiState by viewModel.uiState.collectAsState()
    val dateFormat =
        DateTimeFormatter.ofPattern(stringResource(R.string.format_yyyy_mm_dd_e), Locale.JAPAN)

    val focusRequester = remember { FocusRequester() }
    val focusRequesterDate = remember { FocusRequester() }
    val focusRequesterStartTime = remember { FocusRequester() }
    val focusRequesterEndTime = remember { FocusRequester() }

    var launched by rememberSaveable { mutableStateOf(false) }
    if (launched.not()) {
        LaunchedEffect(Unit) {
            viewModel.event(DetailViewModel.Event.EditInit(detailId = detailId))
            launched = true
            focusRequester.requestFocus()
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
            // 登録が完了すれば、前画面に遷移する
            if (uiState.popBackStackFlag) {
                navController.navigate("detail/${uiState.planId}")
                viewModel.initializePopBackStackFlag()
            }
        },
    ) {
        Column(modifier = modifier) {
            Row(
                modifier = Modifier
                    .background(themeColors.backgroundColor)
                    .padding(7.dp),
            ) {
                Text(
                    text = stringResource(R.string.label_contents),
                    color = themeColors.textColor,
                    fontSize = 18.sp,
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth(1f),
                    text = stringResource(R.string.label_required),
                    color = Color.Red,
                    fontSize = 18.sp,
                )
            }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(start = 5.dp, end = 5.dp)
                    .focusRequester(focusRequester),
                value = uiState.title,
                onValueChange = {
                    viewModel.event(DetailViewModel.Event.TitleChanged(it))
                },
                label = { Text(stringResource(R.string.label_detail_title)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
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
            if (uiState.titleErrorMessage == stringResource(R.string.error_required)) {
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = stringResource(R.string.error_required),
                    color = MaterialTheme.colors.error
                )
            } else if (uiState.titleErrorMessage == stringResource(R.string.error_length_15)) {
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = stringResource(R.string.error_length_15),
                    color = MaterialTheme.colors.error
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .background(themeColors.backgroundColor)
                    .padding(7.dp)
                    .fillMaxWidth(1f),
                color = themeColors.textColor,
                fontSize = 18.sp,
                text = stringResource(R.string.label_date),
            )
            Row(
                modifier = Modifier
                    .padding(start = 5.dp, end = 10.dp, top = 7.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequesterDate),
                    value = if (uiState.date == null) stringResource(R.string.empty) else dateFormat.format(
                        uiState.date
                    ),
                    onValueChange = {},
                    readOnly = true,
                )
                IconButton(
                    modifier = Modifier.width(30.dp),
                    onClick = {
                        viewModel.event(DetailViewModel.Event.ClearDate)
                        focusRequesterDate.requestFocus()
                    }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(R.string.desc_clear)
                    )
                }
                Spacer(modifier = Modifier.width(15.dp))
                IconButton(
                    modifier = Modifier.width(30.dp),
                    onClick = {
                        DatePickerUtil.showDatePicker(
                            context,
                            onDecideDate = { date ->
                                viewModel.event(DetailViewModel.Event.DateChanged(date))
                            },
                        )
                        focusRequesterDate.requestFocus()
                    }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = stringResource(R.string.desc_start_date)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .background(themeColors.backgroundColor)
                    .padding(5.dp)
                    .fillMaxWidth(1f),
                color = themeColors.textColor,
                fontSize = 18.sp,
                text = stringResource(R.string.label_start_time),
            )
            Row(
                modifier = Modifier
                    .padding(start = 5.dp, end = 10.dp, top = 7.dp),
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
                IconButton(
                    modifier = Modifier.width(30.dp),
                    onClick = {
                        viewModel.event(DetailViewModel.Event.ClearStartTime)
                        focusRequesterStartTime.requestFocus()
                    }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(R.string.desc_clear)
                    )
                }
                Spacer(modifier = Modifier.width(15.dp))
                IconButton(
                    modifier = Modifier.width(30.dp),
                    onClick = {
                        TimePickerUtil.showTimePicker(
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
                    .background(themeColors.backgroundColor)
                    .padding(5.dp)
                    .fillMaxWidth(1f),
                color = themeColors.textColor,
                fontSize = 18.sp,
                text = stringResource(R.string.label_end_time),
            )
            Row(
                modifier = Modifier
                    .padding(start = 5.dp, end = 10.dp, top = 7.dp),
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
                IconButton(
                    modifier = Modifier.width(30.dp),
                    onClick = {
                        viewModel.event(DetailViewModel.Event.ClearEndTime)
                        focusRequesterEndTime.requestFocus()
                    }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(R.string.desc_clear)
                    )
                }
                Spacer(modifier = Modifier.width(15.dp))
                IconButton(
                    modifier = Modifier.width(30.dp),
                    onClick = {
                        TimePickerUtil.showTimePicker(
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
                    modifier = Modifier.padding(start = 5.dp),
                    text = stringResource(R.string.error_time_validate),
                    color = MaterialTheme.colors.error
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .background(themeColors.backgroundColor)
                    .padding(5.dp)
                    .fillMaxWidth(1f),
                color = themeColors.textColor,
                fontSize = 18.sp,
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
                label = { Text(stringResource(R.string.label_cost)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                isError = uiState.costErrorMessage.isNotEmpty(),
                trailingIcon = {
                    if (uiState.costErrorMessage.isEmpty()) return@OutlinedTextField
                    Icon(
                        Icons.Filled.Error,
                        stringResource(R.string.desc_error),
                        tint = MaterialTheme.colors.error
                    )
                },
            )
            if (uiState.costErrorMessage.isNotEmpty()) {
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = stringResource(R.string.error_length_20),
                    color = MaterialTheme.colors.error
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .background(themeColors.backgroundColor)
                    .padding(5.dp)
                    .fillMaxWidth(1f),
                color = themeColors.textColor,
                fontSize = 18.sp,
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
                label = { Text(stringResource(R.string.label_link)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Uri,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .background(themeColors.backgroundColor)
                    .padding(5.dp)
                    .fillMaxWidth(1f),
                color = themeColors.textColor,
                fontSize = 18.sp,
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
                label = { Text(stringResource(R.string.label_memo)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )
        }
    }
}