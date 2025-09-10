package com.donglab.screennameviewer.viewer

import androidx.fragment.app.Fragment

interface ScreenNameViewer {
    fun initialize()
    fun registerFragment(fragment: Fragment)
    fun clear()
}