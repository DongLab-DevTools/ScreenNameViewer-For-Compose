package com.donglab.screennameviewer.overlay

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.setPadding
import com.donglab.screennameviewer.config.ClassNameDebugViewerConfig
import com.donglab.screennameviewer.util.dp
import java.lang.ref.WeakReference

internal class ClassNameDebugOverlayManager(
    private val activityRef: WeakReference<Activity>,
) {

    private val activity: Activity?
        get() = activityRef.get()

    private val decorView: ViewGroup? by lazy {
        activity?.window?.decorView as? ViewGroup
    }

    private val statusBarHeight: Int by lazy {
        activity?.windowManager?.let { getStatusBarHeight(it) } ?: 0
    }

    private val params
        get() = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            topMargin = statusBarHeight + config.topMargin.dp
        }

    private var activityNameTextView: TextView? = null
    private var fragmentTextViewLayout: LinearLayout? = null
    private var customLabelLayout: LinearLayout? = null
    private lateinit var config: ClassNameDebugViewerConfig

    fun initialize(config: ClassNameDebugViewerConfig) {
        this.config = config
    }

    fun removeFragmentName(name: String) {
        fragmentTextViewLayout?.findViewWithTag<View>(name)?.let {
            fragmentTextViewLayout?.removeView(it)
        }
    }

    fun addActivityName(name: String) {
        val context: Context = activity ?: return
        val activityParams = params.apply {
            gravity = config.activityGravity
        }

        if (activityNameTextView != null) {
            activityNameTextView?.text = name
        } else {
            activityNameTextView = createClassNameTextView(context, name).apply {
                decorView?.addView(this, activityParams)
            }
        }
    }

    fun addFragmentName(name: String) {
        val context: Context = activity ?: return
        val fragmentLayout = getOrCreateFragmentTextViewLayout()
        val isExist = fragmentLayout?.findViewWithTag<TextView>(name) != null

        if (!isExist) {
            createClassNameTextView(context, name).let { textView ->
                fragmentLayout?.addView(textView)
            }
        }
    }

    private fun getOrCreateFragmentTextViewLayout(): LinearLayout? {
        if (fragmentTextViewLayout == null) {
            setFragmentNameLayout()
        }
        return fragmentTextViewLayout
    }

    fun clearOverlay() {
        decorView?.let { decor ->
            activityNameTextView?.let { decor.removeView(it) }
            fragmentTextViewLayout?.let { decor.removeView(it) }
            customLabelLayout?.let { decor.removeView(it) }
        }
        activityNameTextView = null
        fragmentTextViewLayout = null
        customLabelLayout = null
    }

    private fun setFragmentNameLayout() {
        if (fragmentTextViewLayout != null || activity == null) return

        val fragmentParams = params.apply {
            gravity = config.fragmentGravity
        }

        fragmentTextViewLayout = LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.TRANSPARENT)
        }.also {
            decorView?.addView(it, fragmentParams)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun createClassNameTextView(context: Context, className: String): TextView {
        return TextView(context).apply {
            text = className
            tag = className

            with(config) {
                setTextColor(textColor)
                setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize)
                setBackgroundColor(backgroundColor)
                setPadding(padding)
            }

            setOnTouchListener { _, _ ->
                Toast.makeText(context, className, Toast.LENGTH_SHORT).show()
                parent?.requestDisallowInterceptTouchEvent(false)
                false
            }
        }
    }

    @SuppressLint("InternalInsetResource")
    private fun getStatusBarHeight(windowManager: WindowManager?): Int {
        if (windowManager == null) return 0

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = windowManager.currentWindowMetrics
            val insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.statusBars())
            insets.top
        } else {
            this.activity?.resources?.let { resources ->
                val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
                if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
            } ?: 0
        }
    }

    /**
     * Custom Label 관련 메서드들
     */
    fun addCustomLabel(label: String) {
        val context: Context = activity ?: return
        val customLayout = getOrCreateCustomLabelLayout()
        val isExist = customLayout?.findViewWithTag<TextView>(label) != null

        if (!isExist) {
            createClassNameTextView(context, label).let { textView ->
                customLayout?.addView(textView)
            }
        }
    }

    fun removeCustomLabel(label: String) {
        customLabelLayout?.findViewWithTag<View>(label)?.let {
            customLabelLayout?.removeView(it)
        }
    }

    private fun getOrCreateCustomLabelLayout(): LinearLayout? {
        if (customLabelLayout == null) {
            setCustomLabelLayout()
        }
        return customLabelLayout
    }

    private fun setCustomLabelLayout() {
        if (customLabelLayout != null || activity == null) return

        val customParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            topMargin = statusBarHeight + config.topMargin.dp
            gravity = config.customLabelGravity
        }

        customLabelLayout = LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.TRANSPARENT)
        }.also {
            decorView?.addView(it, customParams)
        }
    }
}