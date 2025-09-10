package com.donglab.screennameviewer.config

/**
 * ScreenNameViewer의 활성화 조건을 정의하는 설정 클래스
 */
data class ScreenNameViewerSetting(
    val debugModeCondition: () -> Boolean,
    val enabledCondition: () -> Boolean
) {
    val isDebugMode: Boolean
        get() = debugModeCondition()
    
    val isEnabled: Boolean
        get() = isDebugMode && enabledCondition()

    companion object {
        fun default(): ScreenNameViewerSetting {
            return ScreenNameViewerSetting(
                debugModeCondition = { false },
                enabledCondition = { false },
            )
        }
    }
}