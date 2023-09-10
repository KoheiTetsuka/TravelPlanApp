package com.android.exemple.planapp.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.android.exemple.planapp.PlanViewModel
import com.android.exemple.planapp.db.entities.Plan

@Composable
fun PlanList(
    plans: List<Plan>,
    navController: NavController,
    viewModel: PlanViewModel
) {
    LazyColumn {
        items(plans) { plan ->
            PlanRow(
                plan = plan,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}