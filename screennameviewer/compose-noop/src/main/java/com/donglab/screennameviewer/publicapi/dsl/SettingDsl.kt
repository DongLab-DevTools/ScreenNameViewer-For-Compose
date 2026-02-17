package com.donglab.screennameviewer.publicapi.dsl

import com.donglab.screennameviewer.publicapi.setting.ScreenNameViewerSetting

/**
 * Noop implementation - does not create setting DSL in release builds.
 */
@DslMarker
annotation class SettingDsl

@SettingDsl
class SettingBuilder {
    var debugModeCondition: Boolean = false
    var enableCondition: Boolean = false

    fun build(): ScreenNameViewerSetting = ScreenNameViewerSetting()
}

internal inline fun setting(block: SettingBuilder.() -> Unit): ScreenNameViewerSetting {
    return ScreenNameViewerSetting()
}
