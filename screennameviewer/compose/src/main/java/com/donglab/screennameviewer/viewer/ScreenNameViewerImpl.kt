package com.donglab.screennameviewer.viewer

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.donglab.screennameviewer.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.config.ScreenNameViewerSetting
import com.donglab.screennameviewer.overlay.renderer.ScreenNameOverlayRenderer
import java.lang.ref.WeakReference

internal class ScreenNameViewerImpl(
    private val activityRef: WeakReference<ComponentActivity>,
    private val overlayRenderer: ScreenNameOverlayRenderer,
    private val config: ScreenNameOverlayConfig,
    private val settings: ScreenNameViewerSetting
) : ScreenNameViewer {

    init {
        require(settings.isDebugMode) {
            "ScreenNameViewer should only be used in debug builds"
        }
    }

    private val activity: ComponentActivity?
        get() = activityRef.get()

    override fun initialize() {
        if (!settings.isEnabled) return

        overlayRenderer.initialize(config)
        activity?.lifecycle?.addObserver(ActivityLifecycleObserver())
    }

    override fun registerFragment(fragment: Fragment) {
        if (!settings.isEnabled) return

        fragment.viewLifecycleOwner.lifecycle.addObserver(FragmentLifecycleObserver(fragment))
    }

    override fun clear() {
        overlayRenderer.clearOverlay()
    }

    private inner class ActivityLifecycleObserver : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
            overlayRenderer.addActivityName(activity?.javaClass?.simpleName ?: "Unknown Activity")
        }

        override fun onDestroy(owner: LifecycleOwner) {
            clear()
        }
    }

    private inner class FragmentLifecycleObserver(private val fragment: Fragment) : DefaultLifecycleObserver {
        private val fragmentName = fragment.javaClass.simpleName
        
        override fun onResume(owner: LifecycleOwner) {
            overlayRenderer.addFragmentName(fragmentName)
        }

        override fun onPause(owner: LifecycleOwner) {
            overlayRenderer.removeFragmentName(fragmentName)
        }

        override fun onDestroy(owner: LifecycleOwner) {
            if (fragment !is DialogFragment) return
            overlayRenderer.removeFragmentName(fragmentName)
        }
    }
}