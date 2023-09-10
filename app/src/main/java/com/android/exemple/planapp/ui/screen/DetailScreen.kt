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
import com.android.exemple.planapp.DetailViewModel
import com.android.exemple.planapp.ui.components.BottomBar
import com.android.exemple.planapp.ui.components.DetailList

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    navController: NavController,
    planId: Int
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "旅行プラン") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("detailCreate/${planId}") }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "新規作成")
            }
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
                text = "予定一覧",
            )
            val details by viewModel.details.collectAsState(initial = emptyList())
            DetailList(
                details = details,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}