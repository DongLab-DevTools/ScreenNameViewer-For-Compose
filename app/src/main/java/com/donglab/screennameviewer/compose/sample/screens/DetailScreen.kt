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
 * Detail screen for testing parameterized navigation.
 * This should appear as "DetailScreen" in the overlay.
 */
@Composable
fun DetailScreen(
    detailId: Int,
    onNavigateBack: () -> Unit,
    onNavigateToNested: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        BasicText(text = "📄 Detail Screen")
        BasicText(text = "Detail ID: $detailId")
        
        BasicText(text = "This screen tests parameterized navigation routes.")
        BasicText(text = "Route: detail/{id}")
        BasicText(text = "Current ID parameter: $detailId")
        
        // Navigation options
        BasicText(
            text = "→ Go to Nested Screen",
            modifier = Modifier.clickable { onNavigateToNested() }
        )
        
        BasicText(
            text = "← Back to Home",
            modifier = Modifier.clickable { onNavigateBack() }
        )
    }
}