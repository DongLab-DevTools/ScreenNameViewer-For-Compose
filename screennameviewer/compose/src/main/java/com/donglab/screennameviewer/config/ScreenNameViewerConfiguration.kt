package com.donglab.screennameviewer.config

import android.app.Application
import com.donglab.screennameviewer.lifecycle.ScreenNameViewerLifecycleHandler

class ScreenNameViewerConfiguration private constructor() {
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
        private var INSTANCE: ScreenNameViewerConfiguration? = null

        fun getInstance(): ScreenNameViewerConfiguration {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ScreenNameViewerConfiguration().also { INSTANCE = it }
            }
        }
    }
}