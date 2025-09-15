package com.donglab.screennameviewer.publicapi.config

import com.donglab.screennameviewer.publicapi.dsl.overlayConfig

/**
 * ScreenNameViewer의 UI 설정을 담당하는 설정 클래스
 */
data class ScreenNameOverlayConfig internal constructor(
    val textSize: Float,
    val textColor: Int,
    val backgroundColor: Int,
    val padding: Int,
    val topMargin: Int,
    val activityGravity: Int,
    val fragmentGravity: Int,
    val composeRouteGravity: Int
) {
    companion object {
        fun default(): ScreenNameOverlayConfig = overlayConfig {}
    }
}