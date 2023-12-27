package com.android.exemple.planapp.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.android.exemple.planapp.db.entities.Property
import com.android.exemple.planapp.ui.viewModel.PropertyViewModel

@Composable
fun PropertyList(
    properties: List<Property>,
    navController: NavController,
    viewModel: PropertyViewModel
) {
    LazyColumn {
        items(properties) { property ->
            PropertyRow(
                property = property,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}