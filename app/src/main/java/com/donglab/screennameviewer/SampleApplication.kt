package com.donglab.screennameviewer

import android.app.Application
import com.donglab.screennameviewer.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.config.ScreenNameViewerConfiguration
import com.donglab.screennameviewer.config.ScreenNameViewerSetting

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ScreenNameViewerConfiguration.initialize(
            application = this,
            settings = ScreenNameViewerSetting(
                debugModeCondition = { true },
                enabledCondition = { true }
            ),
            config = ScreenNameOverlayConfig.defaultConfig()
        )
    }
}