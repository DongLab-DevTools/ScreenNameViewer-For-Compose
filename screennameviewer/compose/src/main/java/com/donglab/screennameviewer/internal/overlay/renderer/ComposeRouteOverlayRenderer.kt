package com.donglab.screennameviewer.internal.overlay.renderer

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.donglab.screennameviewer.publicapi.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.internal.consts.ScreenNameViewerConstants
import com.donglab.screennameviewer.internal.overlay.builder.StyledTextViewBuilder
import com.donglab.screennameviewer.internal.util.dp
import com.donglab.screennameviewer.internal.util.getStatusBarHeight

/**
 * Compose Route Label 전용 오버레이 렌더러
 * Compose Navigation의 Route 정보를 라벨로 표시합니다.
 */
internal class ComposeRouteOverlayRenderer(
    private val context: Context,
    private val decorView: ViewGroup,
    private val config: ScreenNameOverlayConfig,
) {
    
    private var composeRouteLayout: LinearLayout? = null
    
    private val statusBarHeight: Int by lazy {
        context.getStatusBarHeight()
    }
    
    private val textViewBuilder by lazy {
        StyledTextViewBuilder(config)
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
     * Compose Route Label 레이아웃을 생성하거나 반환합니다.
     * Fragment 레이아웃보다 아래(나중에 추가된 인덱스)에 위치합니다.
     */
    private fun getOrCreateComposeRouteLayout(): LinearLayout {
        if (composeRouteLayout == null) {
            val topMargin = statusBarHeight + config.topMargin.dp
            val gravity = config.composeRouteGravity
            
            composeRouteLayout = LinearLayout(context).apply {
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
            decorView.addView(composeRouteLayout, insertIndex, params)
        }
        
        return composeRouteLayout!!
    }
    
    /**
     * Compose Route Label을 추가합니다.
     */
    fun addRoute(label: String) {
        val layout = getOrCreateComposeRouteLayout()
        val textView = textViewBuilder.build(context, label)
        
        if (!hasRoute(label)) {
            layout.addView(textView)
        }
    }
    
    /**
     * Compose Route Label을 제거합니다.
     */
    fun removeRoute(label: String) {
        composeRouteLayout?.findViewWithTag<View>(label)?.let { textView ->
            composeRouteLayout?.removeView(textView)
        }
    }
    
    /**
     * 해당 이름의 Compose Route Label이 이미 존재하는지 확인합니다.
     */
    private fun hasRoute(label: String): Boolean {
        return composeRouteLayout?.findViewWithTag<TextView>(label) != null
    }
    
    /**
     * 모든 Compose Route Label을 제거합니다.
     */
    fun clear() {
        composeRouteLayout?.let { layout ->
            decorView.removeView(layout)
        }
        composeRouteLayout = null
    }
}