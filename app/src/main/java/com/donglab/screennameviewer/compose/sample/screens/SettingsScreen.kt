package com.donglab.screennameviewer.compose.sample.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Settings screen for testing settings-related Composable detection.
 * This should appear as "SettingsScreen" in the overlay.
 */
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    onOpenAboutDialog: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        BasicText(
            text = "⚙️ Settings Screen", 
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        
        // Settings sections
        SettingsGroup(
            title = "General",
            items = listOf(
                "Notifications" to { /* No action */ },
                "Theme" to { /* No action */ },
                "Language" to { /* No action */ }
            )
        )
        
        SettingsGroup(
            title = "Account",
            items = listOf(
                "Privacy" to { /* No action */ },
                "Security" to { /* No action */ },
                "Data & Storage" to { /* No action */ }
            )
        )
        
        SettingsGroup(
            title = "About",
            items = listOf(
                "App Info" to onOpenAboutDialog,
                "Terms of Service" to { /* No action */ },
                "Privacy Policy" to { /* No action */ }
            )
        )
        
        // Back navigation
        BasicText(
            text = "← Back to Home",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable { onNavigateBack() }
        )
    }
}

@Composable
private fun SettingsGroup(
    title: String,
    items: List<Pair<String, () -> Unit>>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        BasicText(text = title)
        
        items.forEach { (itemTitle, action) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { action() }
                    .padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicText(text = "• $itemTitle")
            }
        }
    }
}