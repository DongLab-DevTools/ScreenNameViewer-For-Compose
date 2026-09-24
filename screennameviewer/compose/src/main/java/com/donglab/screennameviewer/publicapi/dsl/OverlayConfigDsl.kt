package com.donglab.screennameviewer.publicapi.dsl

import android.graphics.Color
import android.view.Gravity
import com.donglab.screennameviewer.internal.util.dp
import com.donglab.screennameviewer.publicapi.config.ScreenNameOverlayConfig

/**
 * DSL 스코프 마커 - 중첩된 DSL 블록에서 외부 스코프 접근을 방지
 */
@DslMarker
annotation class OverlayConfigDsl

@OverlayConfigDsl
class OverlayConfigBuilder {
    private var textSize: Float = 12f
    private var textColor: Int = Color.WHITE
    private var backgroundColor: Int = Color.argb(179, 0, 0, 0)
    private var paddingHorizontal: Int = 6.dp
    private var paddingVertical: Int = 2.dp
    private var cornerRadius: Int = 4
    private var topMargin: Int = 52
    private var activityGravity: Int = Gravity.TOP or Gravity.START
    private var fragmentGravity: Int = Gravity.TOP or Gravity.END
    private var composeRouteGravity: Int = Gravity.TOP or Gravity.END
    private var routeTextColor: Int = Color.rgb(255, 204, 0)
    private var routeTextSize: Float? = null

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
            it.padding?.let { padding ->
                paddingHorizontal = padding
                paddingVertical = padding
            }
            it.cornerRadius?.let { cornerRadius = it }
        }
    }

    /**
     * Compose route 라벨의 텍스트 스타일을 설정하는 확장함수
     * 지정하지 않은 값은 route 기본색(노랑)과 [textStyle] 크기를 따릅니다.
     */
    fun routeTextStyle(block: TextStyleScope.() -> Unit) {
        TextStyleScope().apply(block).also {
            it.size?.let { routeTextSize = it }
            it.color?.let { routeTextColor = it }
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
        paddingHorizontal = paddingHorizontal,
        paddingVertical = paddingVertical,
        cornerRadius = cornerRadius,
        topMargin = topMargin,
        activityGravity = activityGravity,
        fragmentGravity = fragmentGravity,
        composeRouteGravity = composeRouteGravity,
        routeTextColor = routeTextColor,
        routeTextSize = routeTextSize ?: textSize,
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

    /** 상하좌우 공통 안쪽 여백 (px) */
    var padding: Int? = null

    /** 배경 모서리 반경 (dp) */
    var cornerRadius: Int? = null
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