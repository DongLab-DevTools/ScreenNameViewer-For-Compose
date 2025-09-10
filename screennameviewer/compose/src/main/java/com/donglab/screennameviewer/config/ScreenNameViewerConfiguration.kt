package com.donglab.screennameviewer.config

import android.app.Application
import com.donglab.screennameviewer.lifecycle.ScreenNameViewerLifecycleHandler

object ScreenNameViewerConfiguration {
    
    private var settings: ScreenNameViewerSetting = ScreenNameViewerSetting.default()
    private var config: ScreenNameOverlayConfig = ScreenNameOverlayConfig.defaultConfig()
    
    /**
     * ScreenNameViewer를 초기화하고 LifecycleHandler를 등록합니다.
     * Application에서 한 번 호출하여 전체 설정을 완료합니다.
     * 
     * @param application Application 인스턴스
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
     * 저장된 설정을 반환합니다.
     * @return ScreenNameViewerSetting
     */
    internal fun getSettings(): ScreenNameViewerSetting = settings
    
    /**
     * 저장된 UI 설정을 반환합니다.
     * @return ScreenNameOverlayConfig
     */
    internal fun getConfig(): ScreenNameOverlayConfig = config
}