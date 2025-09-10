package com.donglab.screennameviewer.internal.viewer

import androidx.fragment.app.Fragment

internal interface ScreenNameViewer {
    fun initialize()
    fun registerFragment(fragment: Fragment)
    fun clear()
}