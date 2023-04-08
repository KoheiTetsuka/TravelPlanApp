package com.android.exemple.planapp.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.android.exemple.planapp.db.entities.Plan

@Composable
fun PlanList(
    plans: List<Plan>,
    onClickRow: (Plan) -> Unit,
    onClickDelete: (Plan) -> Unit
) {
    LazyColumn {
        items(plans) { plan ->
            PlanRow(
                plan = plan,
                onClickRow = onClickRow,
                onClickDelete = onClickDelete,
            )
        }
    }
}