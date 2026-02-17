package com.donglab.screennameviewer.publicapi.setting

import com.donglab.screennameviewer.publicapi.dsl.setting

/**
 * ScreenNameViewer의 활성화 조건을 정의하는 설정 클래스
 */
data class ScreenNameViewerSetting internal constructor(
    private val debugModeCondition: Boolean,
    private val enabledCondition: Boolean,
) {
    val isEnabled: Boolean
        get() = debugModeCondition && enabledCondition

    companion object {

        @JvmStatic
        fun default(): ScreenNameViewerSetting = setting {
            debugModeCondition = false
            enableCondition = false
        }
    }
}