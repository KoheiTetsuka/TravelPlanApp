package com.android.exemple.planapp.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.android.exemple.planapp.DetailViewModel
import com.android.exemple.planapp.db.entities.Detail

@Composable
fun DetailList(
    details: List<Detail>,
    navController: NavController,
    viewModel: DetailViewModel
) {
    LazyColumn {
        items(details) { detail ->
            DetailRow(
                detail = detail,
                navController = navController,
                viewModel = viewModel,
            )
        }
    }
}