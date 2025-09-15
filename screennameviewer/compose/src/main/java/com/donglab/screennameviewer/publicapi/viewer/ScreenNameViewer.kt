package com.donglab.screennameviewer.publicapi.viewer

import android.app.Application
import com.donglab.screennameviewer.publicapi.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.publicapi.setting.ScreenNameViewerSetting
import com.donglab.screennameviewer.publicapi.config.overlayConfig
import com.donglab.screennameviewer.publicapi.setting.setting
import com.donglab.screennameviewer.internal.lifecycle.ScreenNameViewerLifecycleHandler
import com.donglab.screennameviewer.publicapi.config.OverlayConfigBuilder
import com.donglab.screennameviewer.publicapi.setting.SettingBuilder

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
        ScreenNameViewer.settings = settings
        ScreenNameViewer.config = config

        val lifecycleHandler = ScreenNameViewerLifecycleHandler()
        application.registerActivityLifecycleCallbacks(lifecycleHandler)
    }
}