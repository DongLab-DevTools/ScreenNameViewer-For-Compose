package com.donglab.screennameviewer.factory

import androidx.activity.ComponentActivity
import com.donglab.screennameviewer.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.config.ScreenNameViewerSetting
import com.donglab.screennameviewer.overlay.ScreenNameOverlayRenderer
import com.donglab.screennameviewer.viewer.ScreenNameViewer
import com.donglab.screennameviewer.viewer.ScreenNameViewerImpl
import java.lang.ref.WeakReference

object ScreenNameViewerFactory {
    
    /**
     * 설정을 받아 ScreenNameViewer를 생성합니다.
     * 
     * @param activity 대상 Activity
     * @param settings 활성화 조건 설정 (필수)
     * @param config UI 설정 (선택사항)
     */
    fun create(
        activity: ComponentActivity,
        settings: ScreenNameViewerSetting,
        config: ScreenNameOverlayConfig = ScreenNameOverlayConfig.defaultConfig()
    ): ScreenNameViewer {
        return ScreenNameViewerImpl(
            activity = activity,
            overlayManager = ScreenNameOverlayRenderer(WeakReference(activity)),
            config = config,
            settings = settings
        )
    }
}