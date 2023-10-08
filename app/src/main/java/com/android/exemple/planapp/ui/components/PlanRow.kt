package com.android.exemple.planapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.exemple.planapp.R
import com.android.exemple.planapp.db.entities.Plan
import com.android.exemple.planapp.ui.viewModel.PlanViewModel
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun PlanRow(
    plan: Plan,
    viewModel: PlanViewModel,
    navController: NavController,
) {
    val dateFormat = DateTimeFormatter.ofPattern("yyyy年MM月dd日 (E)", Locale.JAPAN)
    val imageModifier = Modifier
        .size(50.dp)
        .border(BorderStroke(1.dp, Color.Black))
        .background(Color.White)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        elevation = 5.dp,
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    navController.navigate("detail/${plan.id}")
                }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_travel),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = imageModifier
            )
            Spacer(modifier = Modifier.padding(end = 10.dp))
            Column {
                Text(
                    text = plan.title,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Text(
                        text = dateFormat.format(plan.startDate),
                        fontSize = 11.sp
                    )
                    Text(
                        text = "〜",
                        fontSize = 11.sp
                    )
                    Text(
                        text = dateFormat.format(plan.endDate),
                        fontSize = 11.sp
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    navController.navigate("edit/${plan.id}")
                }
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "編集")
            }
            IconButton(
                onClick = {
                    viewModel.deletePlan(plan)
                }
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