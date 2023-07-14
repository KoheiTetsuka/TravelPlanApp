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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.exemple.planapp.MainViewModel
import com.android.exemple.planapp.ui.components.BottomBar
import com.android.exemple.planapp.ui.components.PlanList

@Composable
fun InitScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "旅行プラン") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("mainCreate") }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "新規作成")
            }
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        val plans by viewModel.plans.collectAsState(initial = emptyList())
        PlanList(
            plans = plans,
            navController = navController,
            onClickRow = {
//                viewModel.setEditingPlan(it)
//                viewModel.isShowDialog = true
                navController.navigate("detail")
            },
            onClickEdit = {

            },
        )
    }
}