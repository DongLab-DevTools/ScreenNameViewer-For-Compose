package com.donglab.screennameviewer

import android.app.Application
import com.donglab.screennameviewer.publicapi.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.publicapi.ScreenNameViewerSdk
import com.donglab.screennameviewer.publicapi.config.ScreenNameViewerSetting

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ScreenNameViewerSdk.getInstance().initialize(
            application = this,
            settings = ScreenNameViewerSetting(
                debugModeCondition = { true },
                enabledCondition = { true }
            ),
            config = ScreenNameOverlayConfig.default()
        )
    }
}