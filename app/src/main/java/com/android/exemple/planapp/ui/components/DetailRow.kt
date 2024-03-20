package com.android.exemple.planapp.ui.components

import android.graphics.Color.rgb
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.exemple.planapp.R
import com.android.exemple.planapp.db.entities.Detail
import com.android.exemple.planapp.ui.viewmodels.DetailViewModel
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DetailRow(
    detail: Detail,
    navController: NavController,
    viewModel: DetailViewModel,
    modifier: Modifier = Modifier
) {
    val dateFormat =
        DateTimeFormatter.ofPattern(stringResource(R.string.format_mm_dd), Locale.JAPAN)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = modifier) {
                Text(
                    modifier = Modifier
                        .background(Color(255, 245, 157))
                        .width(60.dp),
                    text = if (detail.date == null) stringResource(R.string.label_pending) else dateFormat.format(
                        detail.date
                    ),
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    modifier = Modifier.width(30.dp),
                    text = if (detail.startTime == null) stringResource(R.string.label_pending) else detail.startTime.toString(),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.width(30.dp),
                    text = stringResource(R.string.label_tilde),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.width(30.dp),
                    text = if (detail.endTime == null) stringResource(R.string.label_pending) else detail.endTime.toString(),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = modifier) {
                Text(
                    fontSize = 23.sp,
                    text = detail.title
                )
                Spacer(modifier = Modifier.height(17.dp))
                Text(
                    fontSize = 14.sp,
                    text = if (detail.cost == "") stringResource(R.string.label_yen)
                            + stringResource(R.string.label_hyphen)
                    else stringResource(R.string.label_yen) + detail.cost
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    navController.navigate("DetailEdit/${detail.id}")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.desc_edit)
                )
            }
            IconButton(
                onClick = {
                    viewModel.event(DetailViewModel.Event.OnDeleteDetailClicked(detail))
                    viewModel.event(DetailViewModel.Event.Init(planId = detail.planId))
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