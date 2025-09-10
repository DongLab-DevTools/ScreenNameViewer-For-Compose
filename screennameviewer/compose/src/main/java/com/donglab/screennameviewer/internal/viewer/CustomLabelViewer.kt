package com.donglab.screennameviewer.internal.viewer

/**
 * CustomLabel 전용 뷰어 인터페이스
 * Activity/Fragment와 독립적으로 CustomLabel만 관리합니다.
 */
internal interface CustomLabelViewer {
    
    /**
     * CustomLabel을 추가합니다.
     * 
     * @param label 표시할 라벨 텍스트
     */
    fun addCustomLabel(label: String)
    
    /**
     * CustomLabel을 제거합니다.
     * 
     * @param label 제거할 라벨 텍스트
     */
    fun removeCustomLabel(label: String)
    
    /**
     * 모든 CustomLabel을 제거합니다.
     */
    fun clear()
}