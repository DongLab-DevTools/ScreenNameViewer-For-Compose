package com.donglab.screennameviewer.publicapi.config

/**
 * Noop implementation - does not create overlay configuration in release builds.
 */
data class ScreenNameOverlayConfig internal constructor(
    val textSize: Float = 0f,
    val textColor: Int = 0,
    val backgroundColor: Int = 0,
    val padding: Int = 0,
    val topMargin: Int = 0,
    val activityGravity: Int = 0,
    val fragmentGravity: Int = 0,
    val composeRouteGravity: Int = 0
) {
    companion object {
        @JvmStatic
        fun default(): ScreenNameOverlayConfig = ScreenNameOverlayConfig()
    }
}
