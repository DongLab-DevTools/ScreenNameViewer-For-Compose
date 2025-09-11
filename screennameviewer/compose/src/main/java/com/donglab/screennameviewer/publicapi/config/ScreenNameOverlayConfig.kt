package com.donglab.screennameviewer.publicapi.config

import android.graphics.Color
import android.view.Gravity

/**
 * ScreenNameViewer의 UI 설정을 담당하는 설정 클래스
 */
data class ScreenNameOverlayConfig(
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
        /**
         * 기본 설정을 반환
         */
        @JvmStatic
        fun default(): ScreenNameOverlayConfig = ScreenNameOverlayConfig(
            textSize = 10f,
            textColor = Color.BLUE,
            backgroundColor = Color.argb(50, 200, 200, 200),
            padding = 16,
            topMargin = 52,
            activityGravity = Gravity.TOP or Gravity.START,
            fragmentGravity = Gravity.TOP or Gravity.END,
            composeRouteGravity = Gravity.TOP or Gravity.END
        )
    }
}