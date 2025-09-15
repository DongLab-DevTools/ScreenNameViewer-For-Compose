package com.donglab.screennameviewer.publicapi.config

import android.graphics.Color
import android.view.Gravity


/**
 * ScreenNameOverlayConfig를 위한 메인 DSL Builder
 */
fun overlayConfig(block: OverlayConfigBuilder.() -> Unit): ScreenNameOverlayConfig {
    return OverlayConfigBuilder().apply(block).build()
}

/**
 * 텍스트 스타일 설정을 위한 DSL Builder
 */
class TextStyleBuilder {
    var size: Float = 10f
    var color: Int = Color.BLUE
}

/**
 * 배경 스타일 설정을 위한 DSL Builder
 */
class BackgroundStyleBuilder {
    var color: Int = Color.argb(50, 200, 200, 200)
    var padding: Int = 16
}

/**
 * 위치 설정을 위한 DSL Builder
 */
class PositionBuilder {
    var topMargin: Int = 52
    var activity: Int = Gravity.TOP or Gravity.START
    var fragment: Int = Gravity.TOP or Gravity.END
    var composeRoute: Int = Gravity.TOP or Gravity.END
}

class OverlayConfigBuilder {
    private var textSize: Float = 10f
    private var textColor: Int = Color.BLUE
    private var backgroundColor: Int = Color.argb(50, 200, 200, 200)
    private var padding: Int = 16
    private var topMargin: Int = 52
    private var activityGravity: Int = Gravity.TOP or Gravity.START
    private var fragmentGravity: Int = Gravity.TOP or Gravity.END
    private var composeRouteGravity: Int = Gravity.TOP or Gravity.END

    /**
     * 텍스트 스타일 설정
     */
    fun textStyle(block: TextStyleBuilder.() -> Unit) {
        val textStyle = TextStyleBuilder().apply(block)
        textSize = textStyle.size
        textColor = textStyle.color
    }

    /**
     * 배경 스타일 설정
     */
    fun background(block: BackgroundStyleBuilder.() -> Unit) {
        val background = BackgroundStyleBuilder().apply(block)
        backgroundColor = background.color
        padding = background.padding
    }

    /**
     * 위치 설정
     */
    fun position(block: PositionBuilder.() -> Unit) {
        val position = PositionBuilder().apply(block)
        topMargin = position.topMargin
        activityGravity = position.activity
        fragmentGravity = position.fragment
        composeRouteGravity = position.composeRoute
    }

    /**
     * ScreenNameOverlayConfig 인스턴스 생성
     */
    fun build(): ScreenNameOverlayConfig = ScreenNameOverlayConfig(
        textSize = textSize,
        textColor = textColor,
        backgroundColor = backgroundColor,
        padding = padding,
        topMargin = topMargin,
        activityGravity = activityGravity,
        fragmentGravity = fragmentGravity,
        composeRouteGravity = composeRouteGravity
    )
}