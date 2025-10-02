package com.donglab.screennameviewer.internal.compose.viewer

import android.content.Context
import android.view.ViewGroup
import com.donglab.screennameviewer.publicapi.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.publicapi.setting.ScreenNameViewerSetting
import com.donglab.screennameviewer.internal.overlay.renderer.ComposeRouteOverlayRenderer

/**
 * Compose Route Label 전용 뷰어 구현체
 * Compose Navigation의 Route 정보를 라벨로 표시합니다.
 */
internal class ComposeRouteViewerImpl(
    context: Context,
    decorView: ViewGroup,
    config: ScreenNameOverlayConfig
) : ComposeRouteViewer {

    private val composeRouteRenderer = ComposeRouteOverlayRenderer(context, decorView, config)

    override fun addRoute(route: String) {
        composeRouteRenderer.addRoute(route)
    }
    
    override fun removeRoute(route: String) {
        composeRouteRenderer.removeRoute(route)
    }
    
    override fun clear() {
        composeRouteRenderer.clear()
    }
}