package com.donglab.screennameviewer.publicapi.setting

import com.donglab.screennameviewer.publicapi.dsl.setting

/**
 * ScreenNameViewer의 활성화 조건을 정의하는 설정 클래스
 */
data class ScreenNameViewerSetting internal constructor(
    val debugModeCondition: () -> Boolean,
    val enabledCondition: () -> Boolean
) {
    val isDebugMode: Boolean
        get() = debugModeCondition()
    
    val isEnabled: Boolean
        get() = isDebugMode && enabledCondition()

    companion object {
        fun default(): ScreenNameViewerSetting = setting {
            debugMode { false }
            enabled { false }
        }
    }
}