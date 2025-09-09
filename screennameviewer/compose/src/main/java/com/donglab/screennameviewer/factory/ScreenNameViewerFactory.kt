package com.donglab.screennameviewer.factory

import androidx.activity.ComponentActivity
import androidx.navigation.NavController
import com.donglab.screennameviewer.compose.tracker.NavigationScreenTracker
import com.donglab.screennameviewer.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.config.ScreenNameViewerSetting
import com.donglab.screennameviewer.overlay.renderer.ScreenNameOverlayRenderer
import com.donglab.screennameviewer.viewer.CustomLabelViewer
import com.donglab.screennameviewer.viewer.CustomLabelViewerImpl
import com.donglab.screennameviewer.viewer.ScreenNameViewer
import com.donglab.screennameviewer.viewer.ScreenNameViewerImpl
import java.lang.ref.WeakReference

object ScreenNameViewerFactory {
    
    /**
     * 설정을 받아 ScreenNameViewer를 생성합니다.
     * 
     * @param activity 대상 Activity
     * @param settings 활성화 조건 설정 (필수)
     * @param config UI 설정 (선택사항)
     */
    fun create(
        activity: ComponentActivity,
        settings: ScreenNameViewerSetting,
        config: ScreenNameOverlayConfig = ScreenNameOverlayConfig.defaultConfig()
    ): ScreenNameViewer {
        return ScreenNameViewerImpl(
            activity = activity,
            overlayManager = ScreenNameOverlayRenderer(WeakReference(activity)),
            config = config,
            settings = settings
        )
    }
    
    /**
     * NavigationScreenTracker를 생성합니다.
     * CustomLabel 전용 뷰어를 내부적으로 생성하여 Navigation route를 추적합니다.
     * 
     * @param activity 대상 Activity
     * @param navController NavController 인스턴스
     * @param settings ScreenNameViewer 설정
     * @param config UI 설정 (선택사항)
     * @return NavigationScreenTracker 인스턴스
     */
    fun createNavigationScreenTracker(
        activity: ComponentActivity,
        navController: NavController,
        settings: ScreenNameViewerSetting,
        config: ScreenNameOverlayConfig = ScreenNameOverlayConfig.defaultConfig()
    ): NavigationScreenTracker {
        val decorView = activity.window.decorView as android.view.ViewGroup
        val customLabelViewer = CustomLabelViewerImpl(activity, decorView, config, settings)
        customLabelViewer.initialize()
        
        return NavigationScreenTracker(navController, customLabelViewer)
    }
}