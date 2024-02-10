package com.android.exemple.planapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.exemple.planapp.R
import com.android.exemple.planapp.ui.viewmodels.PropertyViewModel

@Composable
fun PropertyEditScreen(
    navController: NavController,
    viewModel: PropertyViewModel,
    propertyId: Int
) {
    val uiState by viewModel.uiState.collectAsState()
    var hasTitleError: Boolean = uiState.titleErrorMessage.isNotEmpty()

    var launched by rememberSaveable { mutableStateOf(false) }
    if (launched.not()) {
        LaunchedEffect(Unit) {
            viewModel.event(PropertyViewModel.Event.EditInit(id = propertyId))
            launched = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.screen_property_edit)) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, stringResource(R.string.desc_back))
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (!hasTitleError) {
                            viewModel.event(PropertyViewModel.Event.OnUpdatePropertyClicked(
                                uiState,
                                propertyId
                            ))
                        }
                    }) {
                        Icon(Icons.Filled.Add, stringResource(R.string.desc_update))
                    }
                }
            )
        }
    ) {
        Column {
            Row {
                Text(
                    modifier = Modifier
                        .background(
                            color = Color(0xffcccccc)
                        ),
                    text = stringResource(R.string.label_property),
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
                    viewModel.event(PropertyViewModel.Event.TitleChanged(it))
                },
                label = { Text(stringResource(R.string.label_bottle)) },
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
        }
    }
}