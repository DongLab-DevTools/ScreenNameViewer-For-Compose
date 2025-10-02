package com.donglab.screennameviewer.publicapi.dsl

import com.donglab.screennameviewer.publicapi.setting.ScreenNameViewerSetting

/**
 * DSL 스코프 마커 - 중첩된 DSL 블록에서 외부 스코프 접근을 방지
 */
@DslMarker
annotation class SettingDsl

@SettingDsl
class SettingBuilder {
    var debugModeCondition: Boolean = false
    var enableCondition: Boolean = false

    fun build(): ScreenNameViewerSetting = ScreenNameViewerSetting(
        debugModeCondition = debugModeCondition,
        enabledCondition = enableCondition
    )
}


internal inline fun setting(block: SettingBuilder.() -> Unit): ScreenNameViewerSetting {
    return SettingBuilder().apply(block).build()
}