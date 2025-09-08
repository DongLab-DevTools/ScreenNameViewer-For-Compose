package com.donglab.screennameviewer.compose.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.donglab.classnameviewer.viewer.ClassNameDebugViewer

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
    private val debugViewer: ClassNameDebugViewer
) {
    
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
            debugViewer.removeCustomLabel(it)
        }
        
        // 새 route 표시
        debugViewer.addCustomLabel(routeName)
        currentRoute = routeName
    }
    /**
     * 리소스 정리를 위한 cleanup 메서드
     * Activity나 Fragment가 종료될 때 호출해야 함
     */
    fun cleanup() {
        // 현재 표시된 route 제거
        currentRoute?.let { 
            debugViewer.removeCustomLabel(it)
            currentRoute = null
        }
        
        // 리스너 해제
        navController.removeOnDestinationChangedListener(destinationChangedListener)
    }
}