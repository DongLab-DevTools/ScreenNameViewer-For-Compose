package com.donglab.screennameviewer.overlay.renderer

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.donglab.screennameviewer.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.config.ScreenNameViewerConstants
import com.donglab.screennameviewer.overlay.builder.StyledTextViewBuilder
import com.donglab.screennameviewer.util.dp
import com.donglab.screennameviewer.util.getStatusBarHeight

/**
 * CustomLabel 전용 오버레이 렌더러
 * Activity/Fragment와 독립적으로 동작하며, Fragment 레이아웃 다음에 위치합니다.
 */
internal class CustomLabelOverlayRenderer(
    private val context: Context,
    private val decorView: ViewGroup
) {
    
    private var customLabelLayout: LinearLayout? = null
    private lateinit var config: ScreenNameOverlayConfig
    
    private val statusBarHeight: Int by lazy {
        context.getStatusBarHeight()
    }
    
    private val textViewBuilder by lazy {
        StyledTextViewBuilder(config)
    }
    
    fun initialize(config: ScreenNameOverlayConfig) {
        this.config = config
    }
    
    /**
     * decorView에서 Fragment 레이아웃을 tag로 명시적으로 찾아서 그 다음 위치를 반환합니다.
     * Fragment 레이아웃이 없으면 기본 위치를 반환합니다.
     */
    private fun findFragmentLayoutPosition(): Int {
        for (i in 0 until decorView.childCount) {
            val child = decorView.getChildAt(i)

            if (child.tag == ScreenNameViewerConstants.FRAGMENT_LAYOUT_TAG) {
                return i + 1 // Fragment 레이아웃 다음 위치
            }
        }
        return decorView.childCount // 기본 위치 (맨 마지막)
    }
    
    /**
     * CustomLabel 레이아웃을 생성하거나 반환합니다.
     * Fragment 레이아웃보다 아래(나중에 추가된 인덱스)에 위치합니다.
     */
    private fun getOrCreateCustomLabelLayout(): LinearLayout {
        if (customLabelLayout == null) {
            val topMargin = statusBarHeight + config.topMargin.dp
            val gravity = config.customLabelGravity
            
            customLabelLayout = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                setBackgroundColor(Color.TRANSPARENT)
            }
            
            val params = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                this.topMargin = topMargin
                this.gravity = gravity
            }
            
            // Fragment 레이아웃 다음 위치에 삽입
            val insertIndex = findFragmentLayoutPosition()
            decorView.addView(customLabelLayout, insertIndex, params)
        }
        
        return customLabelLayout!!
    }
    
    /**
     * CustomLabel을 추가합니다.
     */
    fun addCustomLabel(label: String) {
        val layout = getOrCreateCustomLabelLayout()
        val textView = textViewBuilder.build(context, label)
        
        if (!hasCustomLabel(label)) {
            layout.addView(textView)
        }
    }
    
    /**
     * CustomLabel을 제거합니다.
     */
    fun removeCustomLabel(label: String) {
        customLabelLayout?.findViewWithTag<View>(label)?.let { textView ->
            customLabelLayout?.removeView(textView)
        }
    }
    
    /**
     * 해당 이름의 CustomLabel이 이미 존재하는지 확인합니다.
     */
    private fun hasCustomLabel(label: String): Boolean {
        return customLabelLayout?.findViewWithTag<TextView>(label) != null
    }
    
    /**
     * 모든 CustomLabel을 제거합니다.
     */
    fun clear() {
        customLabelLayout?.let { layout ->
            decorView.removeView(layout)
        }
        customLabelLayout = null
    }
}