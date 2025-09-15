package com.donglab.screennameviewer.publicapi.config

import android.graphics.Color
import android.view.Gravity

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

        fun default(): ScreenNameOverlayConfig = overlayConfig {
            textStyle {
                size = 10f
                color = Color.BLUE
            }
            background {
                color = Color.argb(50, 200, 200, 200)
                padding = 16
            }
            position {
                topMargin = 52
                activity = Gravity.TOP or Gravity.START
                fragment = Gravity.TOP or Gravity.END
                composeRoute = Gravity.TOP or Gravity.END
            }
        }
    }
}