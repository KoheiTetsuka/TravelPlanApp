package com.android.exemple.planapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.exemple.planapp.db.entities.Plan

@Composable
fun PlanRow(
    plan: Plan,
    onClickRow: (Plan) -> Unit,
    onClickDelete: (Plan) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        elevation = 5.dp,
    ) {
        Row(
            modifier = Modifier
                .clickable{ onClickRow(plan) }
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = plan.title)
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { onClickDelete(plan) }
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "削除")
            }
        }
    }
}

//@Preview
//@Composable
//fun PlanRowPreview() {
//    PlanRow(
//        plan = Plan(title = "プレビュー", description = "", startDate = "", endDate = ""),
//        onClickRow = {},
//        onClickDelete = {},
//    )
//}