package com.app.compose_structure.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.restro.mealdose.R

@Composable
fun CustomDialog(
    text: String,
    title: String = stringResource(id = R.string.app_name),
    onConfirmClick: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = title)
        }, text = {
            Text(text = text)
        },
        confirmButton = {
            TextButton(onClick = onConfirmClick) {
                Text(text = "Okay")
            }
        }
    )

}