package com.donglab.screennameviewer.config

import android.graphics.Color
import android.view.Gravity

/**
 * ClassNameViewer의 UI 설정을 담당하는 설정 클래스
 */
data class ClassNameDebugViewerConfig(
    val textSize: Float,
    val textColor: Int,
    val backgroundColor: Int,
    val padding: Int,
    val topMargin: Int,
    val activityGravity: Int,
    val fragmentGravity: Int,
    val customLabelGravity: Int
) {
    companion object {
        /**
         * 기본 설정을 반환
         */
        @JvmStatic
        fun defaultConfig(): ClassNameDebugViewerConfig = ClassNameDebugViewerConfig(
            textSize = 10f,
            textColor = Color.BLUE,
            backgroundColor = Color.argb(50, 200, 200, 200),
            padding = 16,
            topMargin = 52,
            activityGravity = Gravity.TOP or Gravity.START,
            fragmentGravity = Gravity.TOP or Gravity.END,
            customLabelGravity = Gravity.TOP or Gravity.END
        )
    }
}