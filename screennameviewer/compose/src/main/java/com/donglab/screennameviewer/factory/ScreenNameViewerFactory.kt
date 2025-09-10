package com.donglab.screennameviewer.factory

import android.app.Application
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.navigation.NavController
import com.donglab.screennameviewer.compose.tracker.ComposeScreenNameViewer
import com.donglab.screennameviewer.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.config.ScreenNameViewerSetting
import com.donglab.screennameviewer.lifecycle.ScreenNameViewerLifecycleHandler
import com.donglab.screennameviewer.overlay.renderer.ScreenNameOverlayRenderer
import com.donglab.screennameviewer.viewer.CustomLabelViewerImpl
import com.donglab.screennameviewer.viewer.ScreenNameViewer
import com.donglab.screennameviewer.viewer.ScreenNameViewerImpl
import java.lang.ref.WeakReference

object ScreenNameViewerFactory {
    
    private var settings: ScreenNameViewerSetting = ScreenNameViewerSetting.default()
    private var config: ScreenNameOverlayConfig = ScreenNameOverlayConfig.defaultConfig()
    
    /**
     * ScreenNameViewerFactory를 초기화합니다.
     * Application에서 한 번 호출하여 전역 설정을 저장합니다.
     * 
     * @param settings 활성화 조건 설정
     * @param config UI 설정
     */
    fun initialize(
        application: Application,
        settings: ScreenNameViewerSetting,
        config: ScreenNameOverlayConfig
    ) {
        this.settings = settings
        this.config = config

        val lifecycleHandler = ScreenNameViewerLifecycleHandler()
        application.registerActivityLifecycleCallbacks(lifecycleHandler)
    }

    /**
     * 초기화된 전역 설정을 사용하여 ScreenNameViewer를 생성합니다.
     * 
     * @param activity 대상 Activity
     */
    fun create(
        activity: ComponentActivity
    ): ScreenNameViewer {
        return ScreenNameViewerImpl(
            activity = activity,
            overlayManager = ScreenNameOverlayRenderer(WeakReference(activity)),
            config = config,
            settings = settings
        )
    }
    
    /**
     * NavigationScreenTracker를 생성합니다.
     * CustomLabel 전용 뷰어를 내부적으로 생성하여 Navigation route를 추적합니다.
     * 초기화된 전역 설정을 사용합니다.
     * 
     * @param activity 대상 Activity
     * @param navController NavController 인스턴스
     * @return ComposeScreenNameViewer 인스턴스
     */
    fun createForCompose(
        activity: ComponentActivity,
        navController: NavController
    ): ComposeScreenNameViewer {
        val decorView = activity.window.decorView as ViewGroup
        val customLabelViewer = CustomLabelViewerImpl(activity, decorView, config, settings)
        customLabelViewer.initialize()
        
        return ComposeScreenNameViewer(navController, customLabelViewer)
    }
}