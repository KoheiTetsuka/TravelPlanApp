package com.android.exemple.planapp.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.android.exemple.planapp.db.entities.Property
import com.android.exemple.planapp.ui.viewModel.PropertyViewModel

@Composable
fun PropertyList(
    properties: List<Property>,
    viewModel: PropertyViewModel
) {
    LazyColumn {
        items(properties) { property ->
            PropertyRow(
                property = property,
                viewModel = viewModel
            )
        }
    }
}