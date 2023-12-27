package com.android.exemple.planapp.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.exemple.planapp.db.entities.Detail
import com.android.exemple.planapp.ui.viewModel.DetailViewModel
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DetailRow(
    detail: Detail,
    navController: NavController,
    viewModel: DetailViewModel
) {
    val dateFormat = DateTimeFormatter.ofPattern("MM月dd日", Locale.JAPAN)
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
            Column {
                Text(
                    modifier = Modifier.background(Color.Yellow),
                    text = if (detail.date == null) "未定" else dateFormat.format(detail.date),
                    fontSize = 12.sp,
                )
                Spacer(modifier = Modifier.height(3.dp))
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
            if (detail.date == null) {
                Spacer(modifier = Modifier.width(22.dp))
            }
            Column {
                Text(
                    fontSize = 20.sp,
                    text = detail.title
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    navController.navigate("DetailEdit/${detail.id}")
                }
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "編集")
            }
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