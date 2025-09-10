package com.donglab.screennameviewer.viewer

import android.content.Context
import android.view.ViewGroup
import com.donglab.screennameviewer.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.config.ScreenNameViewerSetting
import com.donglab.screennameviewer.overlay.renderer.CustomLabelOverlayRenderer

/**
 * CustomLabel 전용 뷰어 구현체
 * Context와 ViewGroup만으로 동작하며 Activity에 의존하지 않습니다.
 */
internal class CustomLabelViewerImpl(
    context: Context,
    decorView: ViewGroup,
    private val config: ScreenNameOverlayConfig,
    private val settings: ScreenNameViewerSetting
) : CustomLabelViewer {

    private val customLabelRenderer = CustomLabelOverlayRenderer(context, decorView)
    
    init {
        require(settings.isDebugMode) {
            "CustomLabelViewer should only be used in debug builds"
        }
    }
    
    override fun initialize() {
        if (!settings.isEnabled) return
        customLabelRenderer.initialize(config)
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