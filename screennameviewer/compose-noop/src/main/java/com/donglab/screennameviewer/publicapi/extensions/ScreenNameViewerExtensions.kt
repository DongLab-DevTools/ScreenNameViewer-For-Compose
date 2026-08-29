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

/**
 * Noop overload - Navigation3(NavDisplay) 대응 시그니처. release 빌드에선 추적 없이 content 만 렌더.
 */
@Composable
fun ScreenNameTracker(
    currentRoute: () -> String?,
    content: @Composable () -> Unit,
) {
    // No-op: Just render content without any tracking
    content()
}
