package com.android.exemple.planapp.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.android.exemple.planapp.db.entities.Detail

@Composable
fun DetailList(
    details: List<Detail>,
    onClickRow: (Detail) -> Unit,
    onClickDelete: (Detail) -> Unit,
) {
    LazyColumn {
        items(details) { detail ->
            DetailRow(
                detail = detail,
                onClickRow = onClickRow,
                onClickDelete = onClickDelete
            )
        }
    }
}