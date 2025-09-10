package com.donglab.screennameviewer.extensions

import android.view.ViewGroup
import androidx.activity.ComponentActivity
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
fun ComponentActivity.createScreenNameViewer(): ScreenNameViewer {
    val settings = ScreenNameViewerConfiguration.getSettings()
    val config = ScreenNameViewerConfiguration.getConfig()
    
    return ScreenNameViewerImpl(
        activity = this,
        overlayManager = ScreenNameOverlayRenderer(WeakReference(this)),
        config = config,
        settings = settings
    )
}

/**
 * ComponentActivity에서 ComposeScreenNameViewer를 생성하는 확장 함수입니다.
 * CustomLabel 전용 뷰어를 내부적으로 생성하여 Navigation route를 추적합니다.
 * 초기화된 전역 설정을 사용합니다.
 * 
 * @param navController NavController 인스턴스
 * @return ComposeScreenNameViewer 인스턴스
 */
fun ComponentActivity.createComposeScreenNameViewer(
    navController: NavController
): ComposeScreenNameViewer {
    val settings = ScreenNameViewerConfiguration.getSettings()
    val config = ScreenNameViewerConfiguration.getConfig()
    
    val decorView = window.decorView as ViewGroup
    val customLabelViewer = CustomLabelViewerImpl(this, decorView, config, settings)
    customLabelViewer.initialize()
    
    return ComposeScreenNameViewer(navController, customLabelViewer)
}