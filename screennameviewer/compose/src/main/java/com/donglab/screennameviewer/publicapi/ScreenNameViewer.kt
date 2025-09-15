package com.donglab.screennameviewer.publicapi

import android.app.Application
import com.donglab.screennameviewer.publicapi.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.publicapi.config.ScreenNameViewerSetting
import com.donglab.screennameviewer.internal.lifecycle.ScreenNameViewerLifecycleHandler

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
        this.settings = settings
        this.config = config

        val lifecycleHandler = ScreenNameViewerLifecycleHandler()
        application.registerActivityLifecycleCallbacks(lifecycleHandler)
    }
}