package com.donglab.screennameviewer.internal.viewer

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

    private val composeRouteLabelRenderer = ComposeRouteOverlayRenderer(context, decorView, config)
    
    init {
        require(settings.isDebugMode) {
            "ComposeRouteLabelViewer should only be used in debug builds"
        }
    }

    override fun addRoute(route: String) {
        if (!settings.isEnabled) return
        composeRouteLabelRenderer.addRoute(route)
    }
    
    override fun removeRoute(route: String) {
        if (!settings.isEnabled) return
        composeRouteLabelRenderer.removeRoute(route)
    }
    
    override fun clear() {
        composeRouteLabelRenderer.clear()
    }
}