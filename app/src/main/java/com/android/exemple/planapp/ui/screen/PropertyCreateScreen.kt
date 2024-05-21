package com.android.exemple.planapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material.icons.filled.Error
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.exemple.planapp.R
import com.android.exemple.planapp.ui.theme.LocalThemeColors
import com.android.exemple.planapp.ui.viewmodels.PropertyViewModel

@Composable
fun PropertyCreateScreen(
    viewModel: PropertyViewModel = hiltViewModel(),
    navController: NavController,
    planId: Int,
    modifier: Modifier = Modifier
) {
    val themeColors = LocalThemeColors.current

    val uiState by viewModel.uiState.collectAsState()

    val focusRequester = remember { FocusRequester() }
    var launched by rememberSaveable { mutableStateOf(false) }
    if (launched.not()) {
        LaunchedEffect(Unit) {
            viewModel.event(PropertyViewModel.Event.CreateInit(planId = planId))
            launched = true
            focusRequester.requestFocus()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.screen_property_create)) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("property/${planId}")
                    }) {
                        Icon(Icons.Filled.ArrowBack, stringResource(R.string.desc_back))
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.event(
                            PropertyViewModel.Event.OnCreatePropertyClicked(uiState)
                        )
                    }) {
                        Icon(Icons.Filled.Add, stringResource(R.string.desc_create))
                    }
                }
            )
            // 登録が完了すれば、前画面に遷移する
            if (uiState.popBackStackFlag) {
                navController.navigate("property/${planId}")
                viewModel.initializePopBackStackFlag()
            }
        }
    ) {
        Column(modifier = modifier) {
            Row(
                modifier = modifier
                    .background(themeColors.backgroundColor)
                    .padding(7.dp),
            ) {
                Text(
                    text = stringResource(R.string.label_property),
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
                    viewModel.event(PropertyViewModel.Event.TitleChanged(it))
                },
                label = { Text(stringResource(R.string.label_bottle)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
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
        }
    }
}