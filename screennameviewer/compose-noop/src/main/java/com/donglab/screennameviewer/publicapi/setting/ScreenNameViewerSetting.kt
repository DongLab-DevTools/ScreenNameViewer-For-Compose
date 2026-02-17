package com.donglab.screennameviewer.publicapi.setting

/**
 * Noop implementation - does not create viewer settings in release builds.
 */
data class ScreenNameViewerSetting internal constructor(
    private val debugModeCondition: Boolean = false,
    private val enabledCondition: Boolean = false,
) {
    val isEnabled: Boolean = false

    companion object {
        @JvmStatic
        fun default(): ScreenNameViewerSetting = ScreenNameViewerSetting()
    }
}
