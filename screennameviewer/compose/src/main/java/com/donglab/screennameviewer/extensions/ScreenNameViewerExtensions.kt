package com.donglab.screennameviewer.extensions

import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.NavController
import com.donglab.screennameviewer.compose.tracker.ComposeScreenNameViewer
import com.donglab.screennameviewer.config.ScreenNameViewerConfiguration
import com.donglab.screennameviewer.overlay.renderer.ScreenNameOverlayRenderer
import com.donglab.screennameviewer.viewer.CustomLabelViewerImpl
import com.donglab.screennameviewer.viewer.ScreenNameViewer
import com.donglab.screennameviewer.viewer.ScreenNameViewerImpl
import java.lang.ref.WeakReference

/**
 * ComponentActivity에서 ScreenNameViewer를 생성하는 확장 함수입니다.
 * 초기화된 전역 설정을 사용합니다.
 * 
 * @return ScreenNameViewer 인스턴스
 */
internal fun ComponentActivity.createScreenNameViewer(): ScreenNameViewer {
    val configuration = ScreenNameViewerConfiguration.getInstance()

    return ScreenNameViewerImpl(
        activityRef = WeakReference(this),
        overlayManager = ScreenNameOverlayRenderer(activityRef = WeakReference(this)),
        config = configuration.config,
        settings = configuration.settings
    )
}


/**
 * NavController에 ScreenNameViewer를 활성화하는 Composable 확장 함수입니다.
 * DisposableEffect를 내부에서 처리하여 생명주기를 자동으로 관리합니다.
 */
@Composable
fun NavController.enableScreenNameViewer() {
    val configuration = ScreenNameViewerConfiguration.getInstance()
    val activity = LocalActivity.current as? ComponentActivity ?: return
    val decorView = activity.window?.decorView as? ViewGroup ?: return

    DisposableEffect(this) {
        val customLabelViewer = CustomLabelViewerImpl(activity, decorView, configuration.config, configuration.settings).apply {
            initialize()
        }

        val screenNameViewer = ComposeScreenNameViewer(
            navController = this@enableScreenNameViewer,
            customLabelViewer = customLabelViewer
        )
        
        onDispose {
            screenNameViewer.cleanup()
        }
    }
}