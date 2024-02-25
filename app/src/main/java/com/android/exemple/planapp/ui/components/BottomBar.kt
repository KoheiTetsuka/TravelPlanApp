package com.android.exemple.planapp.ui.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

sealed class Item(var dist: String, var displayName: String, var icon: ImageVector) {
    object Plan : Item("Plan", "プラン", Icons.Filled.Edit)
    object Property : Item("Property", "持ち物", Icons.Filled.List)
}

@Composable
fun BottomBar(navController: NavController, planId: Int) {
    val items = listOf(Item.Plan, Item.Property)

    BottomNavigation {
        items.forEachIndexed { _, item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.dist) },
                label = { Text(item.displayName) },
                selected = true,
                onClick = {
                    if (item.dist == "Plan") {
                        navController.navigate("detail/${planId}")
                    } else if (item.dist == "Property") {
                        navController.navigate("property/${planId}")
                    }
                }
            )
        }
    }
}