package com.donglab.screennameviewer.publicapi

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
        this.settings = settings
        this.config = config

        val lifecycleHandler = ScreenNameViewerLifecycleHandler()
        application.registerActivityLifecycleCallbacks(lifecycleHandler)
    }
}

/**
 * ScreenNameViewer 초기화를 위한 DSL Builder
 */
class ScreenNameViewerInitBuilder(private val application: Application) {
    private var settings: ScreenNameViewerSetting = ScreenNameViewerSetting.default()
    private var config: ScreenNameOverlayConfig = ScreenNameOverlayConfig.default()

    /**
     * 설정 구성
     */
    fun settings(block: SettingBuilder.() -> Unit) {
        settings = setting(block)
    }

    /**
     * 오버레이 구성
     */
    fun config(block: OverlayConfigBuilder.() -> Unit) {
        config = overlayConfig(block)
    }

    /**
     * 초기화 실행
     */
    fun build() {
        ScreenNameViewer.initialize(application, settings, config)
    }
}

fun initScreenNameViewer(application: Application, block: ScreenNameViewerInitBuilder.() -> Unit) {
    ScreenNameViewerInitBuilder(application).apply(block).build()
}