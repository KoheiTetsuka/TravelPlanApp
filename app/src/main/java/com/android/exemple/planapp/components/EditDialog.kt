package com.android.exemple.planapp.components

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.exemple.planapp.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EditDialog(
    viewModel: MainViewModel = hiltViewModel()
) {
    DisposableEffect(Unit) {
        onDispose {
            viewModel.resetProperties()
        }
    }
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    val inputStartYear = remember { mutableStateOf("") }
    val inputStartMonth = remember { mutableStateOf("") }
    val inputStartDay = remember { mutableStateOf("") }
    val inputEndYear = remember { mutableStateOf("") }
    val inputEndMonth = remember { mutableStateOf("") }
    val inputEndDay = remember { mutableStateOf("") }
    var startDateFlg = remember { mutableStateOf(false) }
    var endDateFlg = remember { mutableStateOf(false) }

    var dateFormat = SimpleDateFormat("yyyy年MM月dd日")

    AlertDialog(
        onDismissRequest = { viewModel.isShowDialog = false },
        title = { Text(text = /*if (viewModel.isEditing) "旅行プラン更新" else*/ "旅行プラン新規作成") },
        text = {
            Column {
                Text(text = "タイトル")
                TextField(
                    value = uiState.title,
                    onValueChange = {
                        viewModel.event(MainViewModel.Event.TitleChanged(it))
                    },
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "詳細情報")
                TextField(
                    value = uiState.description,
                    onValueChange = {
                        viewModel.event(MainViewModel.Event.DescriptionChanged(it))
                    },
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "開始日")
                Row(
                    modifier = Modifier
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextField(
                        modifier = Modifier.weight(1f),
                        value =
                        if (startDateFlg.value)
                            inputEndYear.value  + inputEndMonth.value + inputEndDay.value
                        else
                            "",
                        onValueChange = {
//                            viewModel.event(MainViewModel.Event.StartDateChanged(it))
                        }
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    IconButton(
                        modifier = Modifier.width(30.dp),
                        onClick = {
                            showDatePicker(
                                context,
                                onDecideDate = { year, month, day ->
                                    run {
                                        inputStartYear.value = year
                                        inputStartMonth.value = month
                                        inputStartDay.value = day
                                        startDateFlg.value = true
                                    }
                                }
                            )
                        }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "開始日")
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "終了日")
                Row(
                    modifier = Modifier
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextField(
                        modifier = Modifier
                            .weight(1f),
                        value =
                        if (endDateFlg.value)
                            inputEndYear.value + "年" + inputEndMonth.value + "月" + inputEndDay.value + "日"
                        else
                            "",
                        onValueChange = {
//                            viewModel.event(MainViewModel.Event.EndDateChanged())
                        }
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    IconButton(
                        modifier = Modifier.width(30.dp),
                        onClick = {
                            showDatePicker(
                                context,
                                onDecideDate = { year, month, day ->
                                    run {
                                        inputEndYear.value = year
                                        inputEndMonth.value = month
                                        inputEndDay.value = day
                                        endDateFlg.value = true
                                    }
                                }
                            )
                        }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "終了日")
                    }
                }
            }
        },

        buttons = {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    modifier = Modifier.width(110.dp),
                    onClick = { viewModel.isShowDialog = false },
                ) {
                    Text(text = "キャンセル")
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    modifier = Modifier.width(110.dp),
                    onClick = {
                        viewModel.isShowDialog = false
                        /*if (viewModel.isEditing) {
                            viewModel.updateTask()
                        } else {*/
                        viewModel.createPlan()
//                        }
                    },
                ) {
                    Text(text = "OK")
                }
            }
        },
    )
}

fun showDatePicker(
    context: Context,
    onDecideDate: (String, String, String) -> Unit,
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
                pickedYear.toString(),
                (pickedMonth + 1).toString(),
                pickedDay.toString()
            )
        }, year, month, day
    ).show()
}

