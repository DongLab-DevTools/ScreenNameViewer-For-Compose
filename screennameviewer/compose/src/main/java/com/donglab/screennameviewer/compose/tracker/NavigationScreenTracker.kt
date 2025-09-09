package com.donglab.screennameviewer.compose.tracker

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.donglab.screennameviewer.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.config.ScreenNameViewerSetting
import com.donglab.screennameviewer.viewer.CustomLabelViewer
import com.donglab.screennameviewer.viewer.CustomLabelViewerImpl

/**
 * Navigation Screen Tracker for ClassNameViewer integration.
 * 
 * This class automatically tracks navigation destination changes and displays
 * the current route name in the ClassNameViewer overlay.
 * 
 * Usage:
 * ```kotlin
 * val navController = rememberNavController()
 * DisposableEffect(navController) {
 *     val tracker = NavigationScreenTracker(navController, debugViewer)
 *     onDispose { tracker.cleanup() }
 * }
 * ```
 */
@SuppressLint("RestrictedApi")
class NavigationScreenTracker(
    private val navController: NavController,
    private val customLabelViewer: CustomLabelViewer
) {
    
    companion object {
        /**
         * NavigationScreenTracker를 생성합니다.
         * CustomLabel 전용 뷰어를 내부적으로 생성합니다.
         * 
         * @param activity 대상 Activity
         * @param navController NavController 인스턴스
         * @param settings ScreenNameViewer 설정
         * @param config UI 설정 (선택사항)
         * @return NavigationScreenTracker 인스턴스
         */
        fun create(
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
    
    private var currentRoute: String? = null
    private val destinationChangedListener = NavController.OnDestinationChangedListener { _, destination, _ ->
        handleDestinationChanged(destination)
    }
    
    init {
        // Navigation destination 변경 리스너 등록
        navController.addOnDestinationChangedListener(destinationChangedListener)
        
        // 현재 destination이 있다면 즉시 처리
        navController.currentDestination?.let { currentDestination ->
            handleDestinationChanged(currentDestination)
        }
    }
    
    /**
     * Navigation destination 변경 시 호출되는 메서드
     */
    private fun handleDestinationChanged(destination: NavDestination) {
        val routeName = destination.route ?: "UnknownRoute"
        
        // 기존 route 제거
        currentRoute?.let { 
            customLabelViewer.removeCustomLabel(it)
        }
        
        // 새 route 표시
        customLabelViewer.addCustomLabel(routeName)
        currentRoute = routeName
    }

    fun cleanup() {
        // 현재 표시된 route 제거
        currentRoute?.let { 
            customLabelViewer.removeCustomLabel(it)
            currentRoute = null
        }
        
        // 리스너 해제
        navController.removeOnDestinationChangedListener(destinationChangedListener)
    }
}