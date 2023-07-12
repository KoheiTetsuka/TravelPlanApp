package com.android.exemple.planapp.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.android.exemple.planapp.db.entities.Plan

@Composable
fun PlanList(
    plans: List<Plan>,
    onClickRow: (Plan) -> Unit,
    onClickEdit: (Plan) -> Unit,
    navController: NavController
) {
    LazyColumn {
        items(plans) { plan ->
            PlanRow(
                plan = plan,
                navController = navController,
                onClickRow = onClickRow,
                onClickEdit = onClickEdit,
            )
        }
    }
}