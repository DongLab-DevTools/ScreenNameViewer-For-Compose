package com.donglab.screennameviewer.publicapi.config

/**
 * Noop implementation - does not create overlay configuration in release builds.
 */
data class ScreenNameOverlayConfig internal constructor(
    val textSize: Float = 0f,
    val textColor: Int = 0,
    val backgroundColor: Int = 0,
    val paddingHorizontal: Int = 0,
    val paddingVertical: Int = 0,
    val cornerRadius: Int = 0,
    val topMargin: Int = 0,
    val activityGravity: Int = 0,
    val fragmentGravity: Int = 0,
    val composeRouteGravity: Int = 0,
    val routeTextColor: Int = 0,
    val routeTextSize: Float = 0f,
) {
    @Deprecated("좌우/상하 여백이 분리되었습니다.", ReplaceWith("paddingHorizontal"))
    val padding: Int
        get() = paddingHorizontal

    companion object {
        @JvmStatic
        fun default(): ScreenNameOverlayConfig = ScreenNameOverlayConfig()
    }
}
