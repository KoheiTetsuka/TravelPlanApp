package com.android.exemple.planapp.ui.screen

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.exemple.planapp.R
import com.android.exemple.planapp.ui.components.PlanList
import com.android.exemple.planapp.ui.viewmodels.PlanViewModel

@Composable
fun PlanScreen(
    viewModel: PlanViewModel = hiltViewModel(),
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.screen_travel_plan)) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("planCreate") }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.desc_create)
                )
            }
        },
    ) {
        val plans by viewModel.plans.collectAsState(initial = emptyList())
        PlanList(
            plans = plans,
            navController = navController,
            viewModel = viewModel
        )
    }
}