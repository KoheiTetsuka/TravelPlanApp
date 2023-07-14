package com.android.exemple.planapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.exemple.planapp.db.entities.Property

@Composable
fun PropertyRow(
    property: Property,
    onClickDelete: (Property) -> Unit
) {
    var checkedState by remember { mutableStateOf(true) }
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
                }
            )
            Text(
                modifier = Modifier
                    .padding(start = 10.dp),
                text = property.title
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    onClickDelete(property)
                }
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "削除")
            }
        }
    }

}

@Preview
@Composable
fun PropertyRowPreview() {
    PropertyRow(
        property = Property(title = "タイトル"),
        onClickDelete = {},
    )
}
