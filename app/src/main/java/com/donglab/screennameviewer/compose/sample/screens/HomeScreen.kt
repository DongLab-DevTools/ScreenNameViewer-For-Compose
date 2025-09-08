package com.donglab.screennameviewer.compose.sample.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Home screen for testing Composable function name detection.
 * This should appear as "HomeScreen" in the overlay.
 */
@Composable
fun HomeScreen(
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onOpenDialog: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        BasicText(text = "🏠 Home Screen")
        BasicText(text = "ClassNameViewer Compose Test")
        
        // Navigation options
        BasicText(
            text = "→ Go to Detail (ID: 123)",
            modifier = Modifier.clickable { onNavigateToDetail(123) }
        )
        
        BasicText(
            text = "→ Go to Profile",
            modifier = Modifier.clickable { onNavigateToProfile() }
        )
        
        BasicText(
            text = "→ Go to Settings",
            modifier = Modifier.clickable { onNavigateToSettings() }
        )
        
        // Dialog and BottomSheet options
        BasicText(
            text = "📋 Open Dialog",
            modifier = Modifier.clickable { onOpenDialog() }
        )
        
    }
}