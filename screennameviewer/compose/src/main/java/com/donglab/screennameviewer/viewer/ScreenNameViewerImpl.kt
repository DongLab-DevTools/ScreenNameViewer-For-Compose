package com.donglab.screennameviewer.viewer

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.donglab.screennameviewer.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.config.ScreenNameViewerSetting
import com.donglab.screennameviewer.overlay.ScreenNameOverlayRenderer

internal class ScreenNameViewerImpl(
    private val activity: ComponentActivity,
    private val overlayManager: ScreenNameOverlayRenderer,
    private val config: ScreenNameOverlayConfig,
    private val settings: ScreenNameViewerSetting
) : ScreenNameViewer {

    init {
        require(settings.isDebugMode) {
            "ScreenNameViewer should only be used in debug builds"
        }
    }

    override fun initialize() {
        if (!settings.isEnabled) return

        overlayManager.initialize(config)
        activity.lifecycle.addObserver(ActivityLifecycleObserver())
    }

    override fun registerFragment(fragment: Fragment) {
        if (!settings.isEnabled) return

        fragment.viewLifecycleOwner.lifecycle.addObserver(FragmentLifecycleObserver(fragment))
    }

    override fun clear() {
        overlayManager.clearOverlay()
    }

    override fun addCustomLabel(label: String) {
        if (!settings.isEnabled) return
        overlayManager.addCustomLabel(label)
    }

    override fun removeCustomLabel(label: String) {
        if (!settings.isEnabled) return
        overlayManager.removeCustomLabel(label)
    }

    private inner class ActivityLifecycleObserver : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
            overlayManager.addActivityName(activity.javaClass.simpleName)
        }

        override fun onDestroy(owner: LifecycleOwner) {
            clear()
        }
    }

    private inner class FragmentLifecycleObserver(private val fragment: Fragment) : DefaultLifecycleObserver {
        private val fragmentName = fragment.javaClass.simpleName
        
        override fun onResume(owner: LifecycleOwner) {
            overlayManager.addFragmentName(fragmentName)
        }

        override fun onPause(owner: LifecycleOwner) {
            overlayManager.removeFragmentName(fragmentName)
        }

        override fun onDestroy(owner: LifecycleOwner) {
            if (fragment !is DialogFragment) return
            overlayManager.removeFragmentName(fragmentName)
        }
    }
}