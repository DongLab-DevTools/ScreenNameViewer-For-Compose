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
 * Profile screen with complex UI structure for testing.
 * This should appear as "ProfileScreen" in the overlay.
 */
@Composable
fun ProfileScreen(
    onNavigateBack: () -> Unit,
    onOpenEditDialog: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        BasicText(text = "👤 Profile Screen")
        
        // Profile info section
        ProfileInfoSection()
        
        // Action buttons section
        ProfileActionsSection(
            onEditProfile = onOpenEditDialog
        )
        
        // Navigation
        BasicText(
            text = "← Back to Home",
            modifier = Modifier.clickable { onNavigateBack() }
        )
    }
}

@Composable
private fun ProfileInfoSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        BasicText(text = "Profile Information")
        BasicText(text = "Name: John Doe")
        BasicText(text = "Email: john.doe@example.com")
        BasicText(text = "Member since: 2023")
    }
}

@Composable
private fun ProfileActionsSection(
    onEditProfile: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        BasicText(text = "Actions")
        
        BasicText(
            text = "✏️ Edit Profile",
            modifier = Modifier.clickable { onEditProfile() }
        )
    }
}