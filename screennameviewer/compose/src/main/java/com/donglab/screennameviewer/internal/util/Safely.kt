package com.donglab.screennameviewer.internal.util

import android.util.Log
import com.donglab.screennameviewer.publicapi.viewer.ScreenNameViewer
import kotlin.coroutines.cancellation.CancellationException

/**
 * 라이브러리 내부 실패가 연동 앱으로 전파되지 않도록 격리하는 경계 래퍼입니다.
 * 코루틴 취소(CancellationException)는 구조적 동시성 유지를 위해 다시 던집니다.
 */
internal inline fun safely(block: () -> Unit) {
    try {
        block()
    } catch (e: CancellationException) {
        throw e
    } catch (t: Throwable) {
        if (ScreenNameViewer.settings.isEnabled) {
            Log.w("ScreenNameViewer", "isolated failure", t)
        }
    }
}
