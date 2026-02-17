package com.donglab.screennameviewer.publicapi.extensions

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

/**
 * Noop implementation - does not create screen name tracker in release builds.
 */
@Composable
fun ScreenNameTracker(
    navController: NavController,
    content: @Composable () -> Unit
) {
    // No-op: Just render content without any tracking
    content()
}
