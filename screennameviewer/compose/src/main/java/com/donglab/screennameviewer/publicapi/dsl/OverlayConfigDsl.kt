package com.donglab.screennameviewer.publicapi.dsl

import android.graphics.Color
import android.view.Gravity
import com.donglab.screennameviewer.publicapi.config.ScreenNameOverlayConfig

/**
 * DSL 스코프 마커 - 중첩된 DSL 블록에서 외부 스코프 접근을 방지
 */
@DslMarker
annotation class OverlayConfigDsl

@OverlayConfigDsl
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
     * 텍스트 스타일을 설정하는 확장함수
     */
    fun textStyle(block: TextStyleScope.() -> Unit) {
        TextStyleScope().apply(block).also {
            it.size?.let { textSize = it }
            it.color?.let { textColor = it }
        }
    }

    /**
     * 배경 스타일을 설정하는 확장함수
     */
    fun background(block: BackgroundScope.() -> Unit) {
        BackgroundScope().apply(block).also {
            it.color?.let { backgroundColor = it }
            it.padding?.let { padding = it }
        }
    }

    /**
     * 위치를 설정하는 확장함수
     */
    fun position(block: PositionScope.() -> Unit) {
        PositionScope().apply(block).also {
            it.topMargin?.let { topMargin = it }
            it.activity?.let { activityGravity = it }
            it.fragment?.let { fragmentGravity = it }
            it.composeRoute?.let { composeRouteGravity = it }
        }
    }

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

/**
 * 텍스트 스타일 스코프
 */
@OverlayConfigDsl
class TextStyleScope {
    var size: Float? = null
    var color: Int? = null
}

/**
 * 배경 스타일 스코프
 */
@OverlayConfigDsl
class BackgroundScope {
    var color: Int? = null
    var padding: Int? = null
}

/**
 * 위치 설정 스코프
 */
@OverlayConfigDsl
class PositionScope {
    var topMargin: Int? = null
    var activity: Int? = null
    var fragment: Int? = null
    var composeRoute: Int? = null
}

internal inline fun overlayConfig(block: OverlayConfigBuilder.() -> Unit): ScreenNameOverlayConfig {
    return OverlayConfigBuilder().apply(block).build()
}