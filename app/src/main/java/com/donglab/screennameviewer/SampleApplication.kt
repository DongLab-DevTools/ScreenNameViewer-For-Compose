package com.donglab.screennameviewer

import android.app.Application
import com.donglab.screennameviewer.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.config.ScreenNameViewerSetting
import com.donglab.screennameviewer.factory.ScreenNameViewerFactory
import com.donglab.screennameviewer.lifecycle.ScreenNameViewerLifecycleHandler

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val settings = ScreenNameViewerSetting(
            debugModeCondition = { true },
            enabledCondition = { true }
        )
        val config = ScreenNameOverlayConfig.defaultConfig()

        registerActivityLifecycleCallbacks(ScreenNameViewerLifecycleHandler(settings, config))
    }
}