package com.donglab.screennameviewer.compose.sample.screens.nested

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
 * Nested screen for testing deep navigation structures.
 * This should appear as "NestedScreen" in the overlay.
 */
@Composable
fun NestedScreen(
    onNavigateBack: () -> Unit,
    onNavigateToTab: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        BasicText(text = "🔗 Nested Screen")
        BasicText(text = "This tests nested navigation structures")
        BasicText(text = "and deep screen hierarchies.")
        
        // Navigation options
        BasicText(
            text = "→ Go to Tab Navigation",
            modifier = Modifier.clickable { onNavigateToTab() }
        )
        
        BasicText(
            text = "← Back to Detail",
            modifier = Modifier.clickable { onNavigateBack() }
        )
    }
}