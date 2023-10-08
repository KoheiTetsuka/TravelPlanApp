package com.android.exemple.planapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.exemple.planapp.db.entities.Detail
import com.android.exemple.planapp.ui.viewModel.DetailViewModel

@Composable
fun DetailRow(
    detail: Detail,
    navController: NavController,
    viewModel: DetailViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier
                .clickable { navController.navigate("detail/${detail.planId}") }
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = detail.date.toString(),
                fontSize = 15.sp
            )
            Column {
                Text(
                    fontSize = 20.sp,
                    text = detail.title
                )
                Row {
                    Text(
                        text = detail.startTime.toString(),
                        fontSize = 11.sp
                    )
                    Text(
                        text = "〜",
                        fontSize = 11.sp
                    )
                    Text(
                        text = detail.endTime.toString(),
                        fontSize = 11.sp
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { viewModel.deleteDetail(detail) }
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "削除")
            }
        }
    }
}

@Preview
@Composable
fun DetailRowPreview() {
//    DetailRow(
//        detail = Detail(title = "タイトル", cost = "費用", url = "URL", memo = "メモ"),
//        onClickRow = {},
//        onClickDelete = {},
//    )
}