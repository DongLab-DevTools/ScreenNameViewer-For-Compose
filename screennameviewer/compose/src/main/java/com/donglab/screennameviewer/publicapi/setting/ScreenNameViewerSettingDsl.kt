package com.donglab.screennameviewer.publicapi.setting

/**
 * ScreenNameViewerSetting을 위한 DSL Builder
 */
class SettingBuilder {
    private var debugModeCondition: () -> Boolean = { false }
    private var enabledCondition: () -> Boolean = { false }

    /**
     * 디버그 모드 조건 설정
     */
    fun debugMode(condition: () -> Boolean) {
        debugModeCondition = condition
    }

    /**
     * 활성화 조건 설정
     */
    fun enabled(condition: () -> Boolean) {
        enabledCondition = condition
    }

    /**
     * ScreenNameViewerSetting 인스턴스 생성
     */
    fun build(): ScreenNameViewerSetting = ScreenNameViewerSetting(
        debugModeCondition = debugModeCondition,
        enabledCondition = enabledCondition
    )
}

/**
 * DSL 스타일로 ScreenNameViewerSetting을 생성하는 진입점 함수
 *
 * 사용 예시:
 * ```kotlin
 * val setting = setting {
 *     debugMode { BuildConfig.DEBUG }
 *     enabled { true }
 * }
 * ```
 */
fun setting(block: SettingBuilder.() -> Unit): ScreenNameViewerSetting {
    return SettingBuilder().apply(block).build()
}