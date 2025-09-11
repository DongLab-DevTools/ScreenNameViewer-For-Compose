package com.donglab.screennameviewer.publicapi.extensions

import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.NavController
import com.donglab.screennameviewer.internal.compose.tracker.ComposeScreenNameTracker
import com.donglab.screennameviewer.internal.viewer.CustomLabelViewerImpl
import com.donglab.screennameviewer.publicapi.ScreenNameViewer

/**
 * NavController에 ScreenNameViewer를 활성화하는 Composable 확장 함수입니다.
 * DisposableEffect를 내부에서 처리하여 생명주기를 자동으로 관리합니다.
 */
@Composable
fun NavController.enableScreenNameTracker() {
    val configuration = ScreenNameViewer.getInstance()
    val activity = LocalActivity.current as? ComponentActivity ?: return
    val decorView = activity.window?.decorView as? ViewGroup ?: return

    DisposableEffect(this, activity) {
        val screenNameViewer = ComposeScreenNameTracker(
            navController = this@enableScreenNameTracker,
            customLabelViewer = CustomLabelViewerImpl(
                context = activity,
                decorView = decorView,
                config = configuration.config,
                settings = configuration.settings
            )
        )
        
        onDispose {
            screenNameViewer.cleanup()
        }
    }
}