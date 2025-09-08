package com.donglab.screennameviewer.compose

import androidx.activity.ComponentActivity
import androidx.navigation.NavController
import com.donglab.screennameviewer.compose.tracker.NavigationScreenTracker
import com.donglab.screennameviewer.factory.ClassNameViewerFactory
import com.donglab.screennameviewer.config.ClassNameViewerSettings
import com.donglab.screennameviewer.config.ClassNameDebugViewerConfig

/**
 * ClassNameViewer for Compose 헬퍼 클래스
 * 
 * Compose 앱에서 ClassNameViewer와 Navigation 추적을 쉽게 설정할 수 있도록 도와주는 유틸리티 클래스입니다.
 */
object ComposeClassNameViewerFactory {
    
    /**
     * NavController와 함께 NavigationScreenTracker를 생성합니다.
     * 
     * @param navController Navigation controller
     * @param activity ComponentActivity 인스턴스
     * @param settings ClassNameViewer 설정 (optional)
     * @param config ClassNameViewer UI 설정 (optional)
     * @return NavigationScreenTracker 인스턴스
     */
    fun createNavigationTracker(
        navController: NavController,
        activity: ComponentActivity,
        settings: ClassNameViewerSettings,
        config: ClassNameDebugViewerConfig
    ): NavigationScreenTracker {
        val debugViewer = ClassNameViewerFactory.create(activity, settings, config)
        debugViewer.initialize()

        return NavigationScreenTracker(navController, debugViewer)
    }
}