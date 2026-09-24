package com.donglab.screennameviewer.publicapi.extensions

/**
 * route 객체를 라벨용 이름으로 변환합니다.
 * 패키지는 빼고, 중첩 클래스는 바깥 클래스까지 `.` 으로 이어 표시합니다.
 * 예: `com.tving.core.nav.TvingRoute.MoreBand.Tab` -> `TvingRoute.MoreBand.Tab`
 */
fun routeNameOf(route: Any): String =
    route.javaClass.name // com.tving.core.nav.TvingRoute$MoreBand$Tab
        .substringAfterLast('.') // TvingRoute$MoreBand$Tab
        .replace('$', '.') // TvingRoute.MoreBand.Tab
