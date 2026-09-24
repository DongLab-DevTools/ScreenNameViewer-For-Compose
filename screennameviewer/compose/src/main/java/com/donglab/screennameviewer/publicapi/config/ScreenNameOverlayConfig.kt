package com.donglab.screennameviewer.publicapi.config

import com.donglab.screennameviewer.publicapi.dsl.overlayConfig

/**
 * ScreenNameViewer의 UI 설정을 담당하는 설정 클래스
 *
 * @property paddingHorizontal 라벨 좌우 안쪽 여백 (px)
 * @property paddingVertical 라벨 상하 안쪽 여백 (px)
 * @property cornerRadius 라벨 배경 모서리 반경 (dp)
 * @property routeTextColor Compose route 라벨 글자색
 * @property routeTextSize Compose route 라벨 글자 크기 (dp)
 */
data class ScreenNameOverlayConfig internal constructor(
    val textSize: Float,
    val textColor: Int,
    val backgroundColor: Int,
    val paddingHorizontal: Int,
    val paddingVertical: Int,
    val cornerRadius: Int,
    val topMargin: Int,
    val activityGravity: Int,
    val fragmentGravity: Int,
    val composeRouteGravity: Int,
    val routeTextColor: Int,
    val routeTextSize: Float,
) {
    @Deprecated("좌우/상하 여백이 분리되었습니다.", ReplaceWith("paddingHorizontal"))
    val padding: Int
        get() = paddingHorizontal

    companion object {
        @JvmStatic
        fun default(): ScreenNameOverlayConfig = overlayConfig {}
    }
}
