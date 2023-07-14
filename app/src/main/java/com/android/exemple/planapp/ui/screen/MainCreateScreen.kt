package com.android.exemple.planapp.ui.screen

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.widget.DatePicker
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
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.exemple.planapp.MainViewModel
import com.android.exemple.planapp.ui.components.BottomBar
import java.time.LocalDate
import java.util.Date

@Composable
fun MainCreateScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController,
) {
    DisposableEffect(Unit) {
        onDispose {
            viewModel.resetProperties()
        }
    }
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    var hasError: Boolean = uiState.titleErrorMessage.isNotEmpty()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "新規旅行作成") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (!hasError) {
                            viewModel.createPlan()
                        }
                    }) {
                        Icon(Icons.Filled.Add, null)
                    }
                },
            )
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        Column {
            Text(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .background(
                        color = Color(0xffcccccc)
                    ),
                text = "タイトル",
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(start = 5.dp, end = 5.dp),
                value = uiState.title,
                onValueChange = {
                    viewModel.event(MainViewModel.Event.TitleChanged(it))
                },
                isError = uiState.titleErrorMessage.isNotEmpty(),
                label = { Text("例：沖縄旅行") },
                singleLine = true,
                trailingIcon = {
                    if (uiState.titleErrorMessage.isEmpty()) return@OutlinedTextField
                    Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                },
            )
            if (uiState.titleErrorMessage.isNotEmpty()) {
                Text(
                    text = "タイトルの入力は必須です。",
                    color = MaterialTheme.colors.error
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .background(
                        color = Color(0xffcccccc)
                    ),
                text = "詳細情報",
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(start = 5.dp, end = 5.dp),
                value = uiState.description,
                onValueChange = {
                    viewModel.event(MainViewModel.Event.DescriptionChanged(it))
                },
                label = { Text("例：卒業旅行") }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .background(
                        color = Color(0xffcccccc)
                    ),
                text = "開始日",
            )
            Row(
                modifier = Modifier
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = uiState.startDate?.toString() ?: "",
                    onValueChange = {

                    },
                )
                Spacer(modifier = Modifier.width(15.dp))
                IconButton(
                    modifier = Modifier.width(30.dp),
                    onClick = {
                        showDatePicker(
                            context,
                            onDecideDate = { date ->
                                viewModel.event(MainViewModel.Event.StartDateChanged(date))
                            }
                        )
                    }) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "開始日")
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .background(
                        color = Color(0xffcccccc)
                    ),
                text = "終了日",
            )
            Row(
                modifier = Modifier
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f),
                    value = uiState.endDate?.toString() ?: "",
                    onValueChange = {
//                            viewModel.event(MainViewModel.Event.EndDateChanged(it))
                    }
                )
                Spacer(modifier = Modifier.width(15.dp))
                IconButton(
                    modifier = Modifier.width(30.dp),
                    onClick = {
                        showDatePicker(
                            context,
                            onDecideDate = { date ->
                                viewModel.event(MainViewModel.Event.EndDateChanged(date))
                            }
                        )
                    }) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "終了日")
                }
            }
        }
    }
}

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
                LocalDate.of(pickedYear, pickedMonth, pickedDay)
            )
        }, year, month, day
    ).show()
}
