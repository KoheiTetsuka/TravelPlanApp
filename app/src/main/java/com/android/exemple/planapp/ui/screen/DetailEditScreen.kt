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
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.exemple.planapp.ui.viewModel.DetailEditViewModel

@Composable
fun DetailEditScreen(
    navController: NavController,
    viewModel: DetailEditViewModel = hiltViewModel(),
    detailId: Int
) {

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var launched by rememberSaveable { mutableStateOf(false) }
    if (launched.not()) {
        LaunchedEffect(Unit) {
            viewModel.event(DetailEditViewModel.Event.Init(detailId = detailId))
            launched = true
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "スケジュール編集") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.updateDetail(detailId)
                    }) {
                        Icon(Icons.Filled.Add, null)
                    }
                }
            )
        },
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
                    viewModel.event(DetailEditViewModel.Event.TitleChanged(it))
                },
                label = { Text("例:成田空港から沖縄へ出発") },
                singleLine = true,
                isError = uiState.titleErrorMessage.isNotEmpty(),
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
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .background(
                        color = Color(0xffcccccc)
                    ),
                text = "日付",
            )
            Row(
                modifier = Modifier
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = uiState.date?.toString() ?: "",
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
                                viewModel.event(DetailEditViewModel.Event.DateChanged(date))
                            },
                        )
                    }) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "開始日")
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .background(
                        color = Color(0xffcccccc)
                    ),
                text = "開始時間",
            )
            Row(
                modifier = Modifier
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = uiState.startTime?.toString() ?: "",
                    onValueChange = {

                    },
                    isError = uiState.timeErrorMessage.isNotEmpty(),
                    trailingIcon = {
                        if (uiState.timeErrorMessage.isEmpty()) return@OutlinedTextField
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                    },
                )
                Spacer(modifier = Modifier.width(15.dp))
                IconButton(
                    modifier = Modifier.width(30.dp),
                    onClick = {
                        showTimePicker(
                            context,
                            onDecideTime = { time ->
                                viewModel.event(DetailEditViewModel.Event.StartTimeChanged(time))
                            }
                        )
                    }) {
                    Icon(imageVector = Icons.Default.Timer, contentDescription = "開始時間")
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .background(
                        color = Color(0xffcccccc)
                    ),
                text = "終了時間",
            )
            Row(
                modifier = Modifier
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = uiState.endTime?.toString() ?: "",
                    onValueChange = {

                    },
                    isError = uiState.timeErrorMessage.isNotEmpty(),
                    trailingIcon = {
                        if (uiState.timeErrorMessage.isEmpty()) return@OutlinedTextField
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                    },
                )
                Spacer(modifier = Modifier.width(15.dp))
                IconButton(
                    modifier = Modifier.width(30.dp),
                    onClick = {
                        showTimePicker(
                            context,
                            onDecideTime = { time ->
                                viewModel.event(DetailEditViewModel.Event.EndTimeChanged(time))
                            }
                        )
                    }) {
                    Icon(imageVector = Icons.Default.Timer, contentDescription = "終了時間")
                }
            }
            if (uiState.timeErrorMessage.isNotEmpty()) {
                Text(
                    text = "終了時間は開始時間より後の時間を入力してください。",
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
                text = "費用",
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(start = 5.dp, end = 5.dp),
                value = uiState.cost,
                onValueChange = {
                    viewModel.event(DetailEditViewModel.Event.CostChanged(it))
                },
                label = { Text("費用") }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .background(
                        color = Color(0xffcccccc)
                    ),
                text = "URL",
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(start = 5.dp, end = 5.dp),
                value = uiState.url,
                onValueChange = {
                    viewModel.event(DetailEditViewModel.Event.UrlChanged(it))
                },
                label = { Text("リンク") }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .background(
                        color = Color(0xffcccccc)
                    ),
                text = "メモ",
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(150.dp)
                    .padding(start = 5.dp, end = 5.dp),
                value = uiState.memo,
                onValueChange = {
                    viewModel.event(DetailEditViewModel.Event.MemoChanged(it))
                },
                label = { Text("メモ") }
            )
        }
    }
}