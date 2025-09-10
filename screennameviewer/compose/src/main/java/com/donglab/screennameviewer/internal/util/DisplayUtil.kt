package com.donglab.screennameviewer.internal.util

import android.content.res.Resources

internal val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

internal val Float.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()