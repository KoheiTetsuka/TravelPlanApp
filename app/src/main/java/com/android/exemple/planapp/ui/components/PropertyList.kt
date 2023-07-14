package com.android.exemple.planapp.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.android.exemple.planapp.db.entities.Property

@Composable
fun PropertyList(
    properties: List<Property>,
    onClickDelete: (Property) -> Unit,
) {
    LazyColumn {
        items(properties) { property ->
            PropertyRow(
                property = property,
                onClickDelete = onClickDelete
            )
        }
    }
}