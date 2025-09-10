package com.donglab.screennameviewer

import android.app.Application
import com.donglab.screennameviewer.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.config.ScreenNameViewerSetting
import com.donglab.screennameviewer.factory.ScreenNameViewerFactory

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ScreenNameViewerFactory.initialize(
            application = this,
            settings = ScreenNameViewerSetting(
                debugModeCondition = { true },
                enabledCondition = { true }
            ),
            config = ScreenNameOverlayConfig.defaultConfig()
        )
    }
}