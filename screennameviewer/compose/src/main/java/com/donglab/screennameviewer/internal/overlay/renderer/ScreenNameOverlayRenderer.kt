package com.donglab.screennameviewer.internal.overlay.renderer

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.view.children
import com.donglab.screennameviewer.publicapi.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.internal.consts.ScreenNameViewerConstants
import com.donglab.screennameviewer.internal.overlay.builder.OverlayLayoutBuilder
import com.donglab.screennameviewer.internal.overlay.builder.StyledTextViewBuilder
import com.donglab.screennameviewer.internal.overlay.isSameOverlayPosition
import com.donglab.screennameviewer.internal.util.dp
import com.donglab.screennameviewer.internal.util.getStatusBarHeight
import com.donglab.screennameviewer.internal.util.safely
import java.lang.ref.WeakReference

internal class ScreenNameOverlayRenderer(
    private val activityRef: WeakReference<ComponentActivity>,
    private val config: ScreenNameOverlayConfig,
) {

    private enum class OverlayType {
        ACTIVITY, FRAGMENT;

        fun getGravity(config: ScreenNameOverlayConfig): Int = when (this) {
            ACTIVITY -> config.activityGravity
            FRAGMENT -> config.fragmentGravity
        }
    }

    private val activity: ComponentActivity?
        get() = activityRef.get()

    private val decorView: ViewGroup? by lazy {
        activity?.window?.decorView as? ViewGroup
    }

    private val statusBarHeight: Int by lazy {
        activity?.getStatusBarHeight() ?: 0
    }

    private val layoutBuilder by lazy {
        OverlayLayoutBuilder(decorView, activity)
    }

    private val textViewBuilder by lazy {
        StyledTextViewBuilder(config)
    }

    private var activityNameTextView: TextView? = null
    private var fragmentTextViewLayout: LinearLayout? = null

    // route 라벨 묶음 높이가 바뀔 때마다 Fragment 라벨 위치를 다시 맞춤
    private val positionSyncListener = ViewTreeObserver.OnGlobalLayoutListener {
        safely { syncTopMarginBelowComposeRoute() }
    }

    private fun getOrCreateLayout(type: OverlayType): LinearLayout? {
        val topMargin = statusBarHeight + config.topMargin.dp
        val gravityByType = type.getGravity(config)

        return when (type) {
            OverlayType.FRAGMENT -> {
                if (fragmentTextViewLayout == null) {
                    fragmentTextViewLayout = layoutBuilder.createContainer(gravityByType, topMargin)?.apply {
                        tag = ScreenNameViewerConstants.FRAGMENT_LAYOUT_TAG
                    }
                    decorView?.viewTreeObserver?.addOnGlobalLayoutListener(positionSyncListener)
                }
                fragmentTextViewLayout
            }
            OverlayType.ACTIVITY -> null // Activity doesn't use LinearLayout
        }
    }

    /**
     * Fragment 라벨 묶음을 Compose route 라벨 묶음 바로 아래로 내립니다.
     * route 라벨 묶음이 없으면 기본 위치를 유지합니다.
     */
    private fun syncTopMarginBelowComposeRoute() {
        val layout = fragmentTextViewLayout ?: return
        val baseTopMargin = statusBarHeight + config.topMargin.dp
        val composeRouteBottom = findComposeRouteBottom() ?: baseTopMargin

        layout.updateTopMargin(maxOf(baseTopMargin, composeRouteBottom))
    }

    /**
     * Fragment 라벨과 같은 위치에 있는 Compose route 라벨 묶음의 bottom 을 반환합니다.
     * 위치가 다르거나 route 라벨 묶음이 없으면 null 입니다.
     * tracker 가 여러 개면 가장 아래 묶음을 기준으로 합니다.
     */
    private fun findComposeRouteBottom(): Int? {
        val decor = decorView ?: return null
        val isSamePosition = isSameOverlayPosition(
            gravityA = config.fragmentGravity,
            gravityB = config.composeRouteGravity,
            layoutDirection = decor.layoutDirection,
        )

        return if (isSamePosition) {
            decor.children
                .filter { it.tag == ScreenNameViewerConstants.COMPOSE_ROUTE_LAYOUT_TAG }
                .maxOfOrNull { it.bottom }
        } else {
            null
        }
    }

    /**
     * 값이 바뀔 때만 topMargin 을 갱신합니다.
     * 같은 값을 다시 넣으면 layout 이 반복 요청되므로 막습니다.
     */
    private fun View.updateTopMargin(topMargin: Int) {
        val params = layoutParams as? ViewGroup.MarginLayoutParams ?: return
        if (params.topMargin == topMargin) return

        params.topMargin = topMargin
        layoutParams = params
    }

    private fun addTextViewToLayout(context: Context, name: String, type: OverlayType) {
        val layout = getOrCreateLayout(type) ?: return
        val textView = textViewBuilder.build(context, name)
        layoutBuilder.addTextView(layout, textView)
    }

    fun removeFragmentName(name: String) {
        layoutBuilder.removeTextView(fragmentTextViewLayout, name)
    }

    fun addActivityName(name: String) {
        val context: Context = activity ?: return
        
        if (activityNameTextView != null) {
            activityNameTextView?.text = name
        } else {
            val activityParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = statusBarHeight + config.topMargin.dp
                gravity = OverlayType.ACTIVITY.getGravity(config)
            }
            
            activityNameTextView = textViewBuilder.build(context, name).apply {
                decorView?.addView(this, activityParams)
            }
        }
    }

    fun addFragmentName(name: String) {
        val context: Context = activity ?: return
        addTextViewToLayout(context, name, OverlayType.FRAGMENT)
    }


    fun clearOverlay() {
        decorView?.viewTreeObserver?.removeOnGlobalLayoutListener(positionSyncListener)
        decorView?.let { decor ->
            activityNameTextView?.let { decor.removeView(it) }
            fragmentTextViewLayout?.let { decor.removeView(it) }
        }
        activityNameTextView = null
        fragmentTextViewLayout = null
    }
}