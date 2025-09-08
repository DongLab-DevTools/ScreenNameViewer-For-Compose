package com.donglab.screennameviewer.factory

import androidx.activity.ComponentActivity
import com.donglab.screennameviewer.config.ClassNameDebugViewerConfig
import com.donglab.screennameviewer.config.ClassNameViewerSettings
import com.donglab.screennameviewer.overlay.ClassNameDebugOverlayManager
import com.donglab.screennameviewer.viewer.ClassNameDebugViewer
import com.donglab.screennameviewer.viewer.ClassNameDebugViewerImpl
import java.lang.ref.WeakReference

object ClassNameViewerFactory {
    
    /**
     * 설정을 받아 ClassNameDebugViewer를 생성합니다.
     * 
     * @param activity 대상 Activity
     * @param settings 활성화 조건 설정 (필수)
     * @param config UI 설정 (선택사항)
     */
    fun create(
        activity: ComponentActivity,
        settings: ClassNameViewerSettings,
        config: ClassNameDebugViewerConfig = ClassNameDebugViewerConfig.defaultConfig()
    ): ClassNameDebugViewer {
        return ClassNameDebugViewerImpl(
            activity = activity,
            overlayManager = ClassNameDebugOverlayManager(WeakReference(activity)),
            config = config,
            settings = settings
        )
    }
}