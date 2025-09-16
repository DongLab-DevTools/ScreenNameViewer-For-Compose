package com.donglab.screennameviewer.publicapi.dsl

import android.app.Application
import com.donglab.screennameviewer.publicapi.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.publicapi.setting.ScreenNameViewerSetting
import com.donglab.screennameviewer.publicapi.viewer.ScreenNameViewer

/**
 * DSL 스코프 마커 - 중첩된 DSL 블록에서 외부 스코프 접근을 방지
 */
@DslMarker
annotation class ViewerDsl

@ViewerDsl
class ViewerInitBuilder(private val application: Application) {
    private var settings: ScreenNameViewerSetting = ScreenNameViewerSetting.default()
    private var config: ScreenNameOverlayConfig = ScreenNameOverlayConfig.default()

    /**
     * 설정을 DSL로 구성하는 확장함수
     */
    fun settings(block: SettingBuilder.() -> Unit) {
        settings = setting(block)
    }

    /**
     * 오버레이 설정을 DSL로 구성하는 확장함수
     */
    fun config(block: OverlayConfigBuilder.() -> Unit) {
        config = overlayConfig(block)
    }


    /**
     * 초기화 함수 - inline 함수에서 접근하기 위해 public으로 설정
     */
    fun initialize() {
        ScreenNameViewer.initialize(application, settings, config)
    }
}

inline fun initScreenNameViewer(application: Application, block: ViewerInitBuilder.() -> Unit) {
    ViewerInitBuilder(application).apply(block).initialize()
}