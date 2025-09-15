package com.donglab.screennameviewer.publicapi.dsl

import com.donglab.screennameviewer.publicapi.setting.ScreenNameViewerSetting

/**
 * DSL 스코프 마커 - 중첩된 DSL 블록에서 외부 스코프 접근을 방지
 */
@DslMarker
annotation class SettingDsl

@SettingDsl
class SettingBuilder {
    private var debugModeCondition: () -> Boolean = { false }
    private var enabledCondition: () -> Boolean = { false }

    /**
     * 디버그 모드 활성화 조건을 람다로 설정하는 확장함수
     */
    fun debugMode(condition: () -> Boolean) {
        debugModeCondition = condition
    }

    /**
     * 활성화 조건을 람다로 설정하는 확장함수
     */
    fun enabled(condition: () -> Boolean) {
        enabledCondition = condition
    }

    fun build(): ScreenNameViewerSetting = ScreenNameViewerSetting(
        debugModeCondition = debugModeCondition,
        enabledCondition = enabledCondition
    )
}

inline fun setting(block: SettingBuilder.() -> Unit): ScreenNameViewerSetting {
    return SettingBuilder().apply(block).build()
}