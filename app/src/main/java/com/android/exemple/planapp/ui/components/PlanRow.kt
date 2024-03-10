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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.exemple.planapp.R
import com.android.exemple.planapp.db.entities.Plan
import com.android.exemple.planapp.ui.viewmodels.PlanViewModel
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun PlanRow(
    plan: Plan,
    viewModel: PlanViewModel,
    navController: NavController,
) {
    val dateFormat =
        DateTimeFormatter.ofPattern(stringResource(R.string.format_yyyy_mm_dd_e), Locale.JAPAN)
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
                    fontSize = 25.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Text(
                        text = if (plan.startDate == null) stringResource(R.string.label_pending) else dateFormat.format(
                            plan.startDate
                        ),
                        fontSize = 15.sp
                    )
                    Text(
                        text = stringResource(R.string.label_tilde),
                        fontSize = 15.sp
                    )
                    Text(
                        text = if (plan.endDate == null) stringResource(R.string.label_pending) else dateFormat.format(
                            plan.endDate
                        ),
                        fontSize = 15.sp
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    navController.navigate("planEdit/${plan.id}")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.desc_edit)
                )
            }
            IconButton(
                onClick = {
                    viewModel.event(PlanViewModel.Event.OnDeletePlanClicked(plan))
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.desc_delete)
                )
            }
        }
    }
}