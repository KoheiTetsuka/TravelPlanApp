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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.exemple.planapp.db.entities.Property
import com.android.exemple.planapp.ui.viewModel.PropertyViewModel

@Composable
fun PropertyRow(
    property: Property,
    viewModel: PropertyViewModel
) {
    var checkedState by remember { mutableStateOf(false) }

    val deleteFlag = property.deleteFlag
    if (deleteFlag.equals("1")) {
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
                    viewModel.softDeleteProperty(property)
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
                    viewModel.deleteProperty(property)
                }
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "削除")
            }
        }
    }

}
