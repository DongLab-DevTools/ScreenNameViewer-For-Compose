package com.donglab.screennameviewer.viewer

import androidx.fragment.app.Fragment

interface ClassNameDebugViewer {
    fun initialize()
    fun registerFragment(fragment: Fragment)
    fun clear()
    
    // Custom Label
    fun addCustomLabel(label: String)
    fun removeCustomLabel(label: String)
}