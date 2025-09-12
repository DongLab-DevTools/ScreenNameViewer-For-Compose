package com.donglab.screennameviewer.internal.compose.viewer

/**
 * Compose Route Label 전용 뷰어 인터페이스
 * Compose Navigation의 Route 정보를 라벨로 표시합니다.
 */
internal interface ComposeRouteViewer {
    
    /**
     * Compose Route Label을 추가합니다.
     * 
     * @param route 표시할 라벨 텍스트
     */
    fun addRoute(route: String)
    
    /**
     * Compose Route Label을 제거합니다.
     * 
     * @param route 제거할 라벨 텍스트
     */
    fun removeRoute(route: String)
    
    /**
     * 모든 Compose Route Label을 제거합니다.
     */
    fun clear()
}