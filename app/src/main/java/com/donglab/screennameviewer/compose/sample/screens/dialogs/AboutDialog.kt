package com.donglab.screennameviewer.compose.sample.screens.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
 * About Dialog for testing dialog detection in different contexts.
 * This should appear as "AboutDialog" in the overlay when shown.
 */
@Composable
fun AboutDialog(
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        AboutDialogContent(onDismiss = onDismiss)
    }
}

@Composable
private fun AboutDialogContent(
    onDismiss: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(320.dp)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Dialog title
            BasicText(text = "ℹ️ About ClassNameViewer")
            
            // App information
            AppInfoSection()
            
            // Close button
            BasicText(
                text = "OK",
                modifier = Modifier.clickable { onDismiss() }
            )
        }
    }
}

@Composable
private fun AppInfoSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicText(text = "Version: 1.0.0")
        BasicText(text = "Build: Debug")
        BasicText(text = "")
        BasicText(text = "A debug library for displaying")
        BasicText(text = "Activity and Fragment class names")
        BasicText(text = "as screen overlays.")
        BasicText(text = "")
        BasicText(text = "Now with enhanced Compose support!")
        BasicText(text = "")
        BasicText(text = "© 2025 DongLab-DevTools")
    }
}