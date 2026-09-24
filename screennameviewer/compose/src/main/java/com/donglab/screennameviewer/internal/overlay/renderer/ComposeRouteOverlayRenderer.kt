package com.donglab.screennameviewer.internal.overlay.renderer

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.donglab.screennameviewer.publicapi.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.internal.overlay.OverlayColumn
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
     * Compose Route Label 레이아웃을 생성하거나 반환합니다.
     * 같은 gravity 의 Fragment 라벨보다 위에 쌓입니다.
     */
    private fun getOrCreateComposeRouteLayout(): LinearLayout {
        composeRouteLayout?.let { return it }

        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.TRANSPARENT)
        }.also { layout ->
            OverlayColumn.attach(
                decorView = decorView,
                group = layout,
                slot = OverlayColumn.Slot.COMPOSE_ROUTE,
                gravity = config.composeRouteGravity,
                topMargin = statusBarHeight + config.topMargin.dp,
            )
            composeRouteLayout = layout
        }
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
        composeRouteLayout?.let(OverlayColumn::detach)
        composeRouteLayout = null
    }
}