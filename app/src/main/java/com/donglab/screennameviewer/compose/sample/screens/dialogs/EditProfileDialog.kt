package com.donglab.screennameviewer.compose.sample.screens.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

/**
 * Edit Profile Dialog for testing dialog Composable detection.
 * This should appear as "EditProfileDialog" in the overlay when shown.
 */
@Composable
fun EditProfileDialog(
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        EditProfileDialogContent(
            onDismiss = onDismiss,
            onSave = onSave
        )
    }
}

@Composable
private fun EditProfileDialogContent(
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(300.dp)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Dialog title
            BasicText(
                text = "✏️ Edit Profile",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            
            // Form fields (simulated)
            FormField(label = "Name", value = "John Doe")
            FormField(label = "Email", value = "john.doe@example.com")
            FormField(label = "Bio", value = "Software Developer")
            
            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End)
            ) {
                BasicText(
                    text = "Cancel",
                    modifier = Modifier.clickable { onDismiss() }
                )
                
                BasicText(
                    text = "Save",
                    modifier = Modifier.clickable { 
                        onSave()
                        onDismiss()
                    }
                )
            }
        }
    }
}

@Composable
private fun FormField(label: String, value: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        BasicText(text = "$label:")
        BasicText(text = "[$value]") // Simulated text field
    }
}