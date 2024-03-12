package com.android.exemple.planapp.ui.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.android.exemple.planapp.R

@Composable
fun BottomBar(navController: NavController, planId: Int) {

    BottomNavigation {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Edit, contentDescription = stringResource(R.string.desc_schedule)) },
            label = { Text( text = stringResource(R.string.label_schedule)) },
            selected = true,
            onClick = {
                    navController.navigate("detail/${planId}")
            }
        )

        BottomNavigationItem(
            icon = { Icon(Icons.Filled.List, contentDescription = stringResource(R.string.desc_property)) },
            label = { Text( text = stringResource(R.string.label_property)) },
            selected = true,
            onClick = {
                    navController.navigate("property/${planId}")
            }
        )
    }
}