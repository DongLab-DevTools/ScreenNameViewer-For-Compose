package com.donglab.screennameviewer.internal.viewer

import android.content.Context
import android.view.ViewGroup
import com.donglab.screennameviewer.publicapi.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.publicapi.config.ScreenNameViewerSetting
import com.donglab.screennameviewer.internal.overlay.renderer.CustomLabelOverlayRenderer

/**
 * CustomLabel 전용 뷰어 구현체
 * Context와 ViewGroup만으로 동작하며 Activity에 의존하지 않습니다.
 */
internal class CustomLabelViewerImpl(
    context: Context,
    decorView: ViewGroup,
    config: ScreenNameOverlayConfig,
    private val settings: ScreenNameViewerSetting
) : CustomLabelViewer {

    private val customLabelRenderer = CustomLabelOverlayRenderer(context, decorView, config)
    
    init {
        require(settings.isDebugMode) {
            "CustomLabelViewer should only be used in debug builds"
        }
    }

    override fun addCustomLabel(label: String) {
        if (!settings.isEnabled) return
        customLabelRenderer.addCustomLabel(label)
    }
    
    override fun removeCustomLabel(label: String) {
        if (!settings.isEnabled) return
        customLabelRenderer.removeCustomLabel(label)
    }
    
    override fun clear() {
        customLabelRenderer.clear()
    }
}