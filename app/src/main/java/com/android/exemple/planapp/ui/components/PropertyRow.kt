package com.android.exemple.planapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.exemple.planapp.R
import com.android.exemple.planapp.db.entities.Property
import com.android.exemple.planapp.ui.viewmodels.PropertyViewModel

@Composable
fun PropertyRow(
    property: Property,
    viewModel: PropertyViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    var checkedState by remember { mutableStateOf(false) }
    val deleteFlag = property.deleteFlag
    if (deleteFlag == stringResource(R.string.deleteFlag)) {
        checkedState = true
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checkedState,
                onCheckedChange = {
                    checkedState = it
                    viewModel.event(
                        PropertyViewModel.Event.OnSoftDeletePropertyClicked(
                            property
                        )
                    )
                    viewModel.event(PropertyViewModel.Event.Init(planId = property.planId))
                },
            )
            Text(
                modifier = Modifier
                    .padding(start = 10.dp),
                text = property.title
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    navController.navigate("propertyEdit/${property.id}")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.desc_edit)
                )
            }
            IconButton(
                onClick = {
                    viewModel.event(PropertyViewModel.Event.OnDeletePropertyClicked(property))
                    viewModel.event(PropertyViewModel.Event.Init(planId = property.planId))
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
