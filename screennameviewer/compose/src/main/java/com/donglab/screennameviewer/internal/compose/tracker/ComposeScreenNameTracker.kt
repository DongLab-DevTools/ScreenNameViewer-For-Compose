package com.donglab.screennameviewer.internal.compose.tracker

import android.annotation.SuppressLint
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.donglab.screennameviewer.internal.compose.viewer.ComposeRouteViewer

/**
 * Navigation Screen Tracker for ScreenNameViewer integration.
 * 
 * This class automatically tracks navigation destination changes and displays
 * the current route name in the ScreenNameViewer overlay using ComposeRouteLabelViewer.
 * 
 * Usage:
 * ```kotlin
 * val navController = rememberNavController()
 * val activity = LocalContext.current as ComponentActivity
 * 
 * DisposableEffect(navController) {
 *     val tracker = NavigationScreenTrackerFactory.create(
 *         activity = activity,
 *         navController = navController,
 *         settings = ScreenNameViewerSetting(
 *             debugModeCondition = { true },
 *             enabledCondition = { true }
 *         )
 *     )
 *     onDispose { tracker.cleanup() }
 * }
 * ```
 */
@SuppressLint("RestrictedApi")
internal class ComposeScreenNameTracker internal constructor(
    private val navController: NavController,
    private val composeRouteViewer: ComposeRouteViewer
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
            composeRouteViewer.removeRoute(it)
        }
        
        // 새 route 표시
        composeRouteViewer.addRoute(routeName)
        currentRoute = routeName
    }

    fun cleanup() {
        // 현재 표시된 route 제거
        currentRoute?.let { 
            composeRouteViewer.removeRoute(it)
            currentRoute = null
        }
        
        // 리스너 해제
        navController.removeOnDestinationChangedListener(destinationChangedListener)
        composeRouteViewer.clear()
    }
}