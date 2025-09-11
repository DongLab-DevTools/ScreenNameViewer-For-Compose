package com.donglab.screennameviewer.internal.viewer

import androidx.fragment.app.Fragment

internal interface ComponentNameViewer {
    fun registerFragment(fragment: Fragment)
    fun clear()
}