package com.donglab.screennameviewer.overlay

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.donglab.screennameviewer.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.util.dp
import com.donglab.screennameviewer.util.getStatusBarHeight
import java.lang.ref.WeakReference

internal class ScreenNameOverlayRenderer(
    private val activityRef: WeakReference<Activity>,
) {

    private enum class OverlayType {
        ACTIVITY, FRAGMENT;

        fun getGravity(config: ScreenNameOverlayConfig): Int = when (this) {
            ACTIVITY -> config.activityGravity
            FRAGMENT -> config.fragmentGravity
        }
    }

    private val activity: Activity?
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
    private lateinit var config: ScreenNameOverlayConfig

    fun initialize(config: ScreenNameOverlayConfig) {
        this.config = config
    }

    private fun getOrCreateLayout(type: OverlayType): LinearLayout? {
        val topMargin = statusBarHeight + config.topMargin.dp
        val gravityByType = type.getGravity(config)

        return when (type) {
            OverlayType.FRAGMENT -> {
                if (fragmentTextViewLayout == null) {
                    fragmentTextViewLayout = layoutBuilder.createContainer(gravityByType, topMargin)
                }
                fragmentTextViewLayout
            }
            OverlayType.ACTIVITY -> null // Activity doesn't use LinearLayout
        }
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
        decorView?.let { decor ->
            activityNameTextView?.let { decor.removeView(it) }
            fragmentTextViewLayout?.let { decor.removeView(it) }
        }
        activityNameTextView = null
        fragmentTextViewLayout = null
    }
}