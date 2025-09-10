package com.donglab.screennameviewer.publicapi

import android.app.Application
import com.donglab.screennameviewer.publicapi.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.publicapi.config.ScreenNameViewerSetting
import com.donglab.screennameviewer.internal.lifecycle.ScreenNameViewerLifecycleHandler

class ScreenNameViewerSdk private constructor() {
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

    companion object {
        @Volatile
        private var INSTANCE: ScreenNameViewerSdk? = null

        fun getInstance(): ScreenNameViewerSdk {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ScreenNameViewerSdk().also { INSTANCE = it }
            }
        }
    }
}