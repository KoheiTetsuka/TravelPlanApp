package com.android.exemple.planapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.exemple.planapp.ui.components.BottomBar
import com.android.exemple.planapp.ui.components.PropertyList
import com.android.exemple.planapp.ui.viewModel.PropertyViewModel

@Composable
fun PropertyScreen (
    viewModel: PropertyViewModel = hiltViewModel(),
    navController: NavController,
    planId: Int
) {

    val uiState by viewModel.uiState.collectAsState()
    viewModel.event(PropertyViewModel.Event.Init(planId = planId))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "持ち物") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("propertyCreate/${planId}") }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "新規作成")
            }
        },
        bottomBar = {
            BottomBar(navController = navController, planId = planId)
        },
    ) {
        Column {
            Text(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .background(
                        color = Color(0xffcccccc)
                    ),
                text = "持ち物一覧",
            )
            val properties = uiState.properties
            if (properties != null) {
                PropertyList(
                    properties = properties,
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }

    }
}