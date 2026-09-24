package com.donglab.screennameviewer.internal.compose.viewer

import android.content.Context
import android.view.ViewGroup
import com.donglab.screennameviewer.publicapi.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.internal.overlay.renderer.ComposeRouteOverlayRenderer
import com.donglab.screennameviewer.internal.util.safely

/**
 * Compose Route Label 전용 뷰어 구현체
 * Compose Navigation의 Route 정보를 라벨로 표시합니다.
 * 뷰 조작(앱과의 경계) 실패를 격리해 예외가 호출부로 전파되지 않도록 합니다.
 */
internal class ComposeRouteViewerImpl(
    context: Context,
    decorView: ViewGroup,
    config: ScreenNameOverlayConfig
) : ComposeRouteViewer {

    private val composeRouteRenderer = ComposeRouteOverlayRenderer(context, decorView, config)

    override fun addRoute(route: String) = safely { composeRouteRenderer.addRoute(route) }

    override fun removeRoute(route: String) = safely { composeRouteRenderer.removeRoute(route) }

    override fun clear() = safely { composeRouteRenderer.clear() }
}
