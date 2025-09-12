package com.donglab.screennameviewer.internal.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.view.WindowInsets
import android.view.WindowManager
import androidx.core.content.getSystemService
import kotlin.let


@SuppressLint("InternalInsetResource")
internal fun Context?.getStatusBarHeight(): Int {
    if (this == null) return 0

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowManager = getSystemService<WindowManager>() ?: return 0
        val windowMetrics = windowManager.currentWindowMetrics
        val insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.statusBars())
        insets.top
    } else {
        resources?.let { resources ->
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
        } ?: 0
    }
}

internal fun Context?.findActivityContext(): Activity? {
    return generateSequence(this) { (it as? ContextWrapper)?.baseContext }
        .filterIsInstance<Activity>()
        .firstOrNull()
}