package com.donglab.screennameviewer.internal.compose.viewer

import android.content.Context
import android.view.ViewGroup
import com.donglab.screennameviewer.publicapi.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.publicapi.config.ScreenNameViewerSetting
import com.donglab.screennameviewer.internal.overlay.renderer.ComposeRouteOverlayRenderer

/**
 * Compose Route Label 전용 뷰어 구현체
 * Compose Navigation의 Route 정보를 라벨로 표시합니다.
 */
internal class ComposeRouteViewerImpl(
    context: Context,
    decorView: ViewGroup,
    config: ScreenNameOverlayConfig,
    private val settings: ScreenNameViewerSetting
) : ComposeRouteViewer {

    private val composeRouteRenderer = ComposeRouteOverlayRenderer(context, decorView, config)
    
    init {
        require(settings.isDebugMode) {
            "ComposeRouteViewer should only be used in debug builds"
        }
    }

    override fun addRoute(route: String) {
        if (!settings.isEnabled) return
        composeRouteRenderer.addRoute(route)
    }
    
    override fun removeRoute(route: String) {
        if (!settings.isEnabled) return
        composeRouteRenderer.removeRoute(route)
    }
    
    override fun clear() {
        composeRouteRenderer.clear()
    }
}