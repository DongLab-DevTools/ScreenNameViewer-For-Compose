package com.donglab.screennameviewer.publicapi.extensions

import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.donglab.screennameviewer.internal.compose.tracker.ComposeScreenNameTracker
import com.donglab.screennameviewer.internal.util.findActivityContext
import com.donglab.screennameviewer.internal.compose.viewer.ComposeRouteViewerImpl
import com.donglab.screennameviewer.publicapi.viewer.ScreenNameViewer

/**
 * ComposeScreenNameTracker를 활성화하는 Composable 함수입니다.
 *
 * @param navController 디버깅할 NavController
 * @param content 하위 Composable 콘텐츠
 */
@Composable
fun ScreenNameTracker(
    navController: NavController,
    content: @Composable () -> Unit
) {
    content()
    
    val context = LocalContext.current
    val activity = context.findActivityContext() as? ComponentActivity ?: return
    val decorView = activity.window?.decorView as? ViewGroup ?: return

    DisposableEffect(navController, activity) {
        val screenNameViewer = ComposeScreenNameTracker(
            navController = navController,
            composeRouteViewer = ComposeRouteViewerImpl(
                context = activity,
                decorView = decorView,
                config = ScreenNameViewer.config,
            )
        )

        onDispose {
            screenNameViewer.cleanup()
        }
    }
}