package com.donglab.screennameviewer.publicapi.dsl

import android.app.Application
import com.donglab.screennameviewer.publicapi.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.publicapi.setting.ScreenNameViewerSetting
import com.donglab.screennameviewer.publicapi.viewer.ScreenNameViewer

/**
 * Noop implementation - does not create viewer DSL in release builds.
 */
@DslMarker
annotation class ViewerDsl

@ViewerDsl
class ViewerInitBuilder(private val application: Application) {
    private var settings: ScreenNameViewerSetting = ScreenNameViewerSetting.default()
    private var config: ScreenNameOverlayConfig = ScreenNameOverlayConfig.default()

    fun settings(block: SettingBuilder.() -> Unit) {}
    fun config(block: OverlayConfigBuilder.() -> Unit) {}

    fun initialize() {
        ScreenNameViewer.initialize(application, settings, config)
    }
}

inline fun initScreenNameViewer(application: Application, block: ViewerInitBuilder.() -> Unit) {
    // Noop: Block is executed but does nothing
}
