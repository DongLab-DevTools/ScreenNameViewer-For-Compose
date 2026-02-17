package com.donglab.screennameviewer.publicapi.viewer

import android.app.Application
import com.donglab.screennameviewer.publicapi.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.publicapi.setting.ScreenNameViewerSetting

/**
 * Noop implementation - does not create screen name overlay in release builds.
 */
object ScreenNameViewer {
    var settings: ScreenNameViewerSetting = ScreenNameViewerSetting.default()
        private set
    var config: ScreenNameOverlayConfig = ScreenNameOverlayConfig.default()
        private set

    fun initialize(
        application: Application,
        settings: ScreenNameViewerSetting,
        config: ScreenNameOverlayConfig
    ) {
        // No-op: Store settings and config but don't register lifecycle callbacks
        ScreenNameViewer.settings = settings
        ScreenNameViewer.config = config
    }
}
