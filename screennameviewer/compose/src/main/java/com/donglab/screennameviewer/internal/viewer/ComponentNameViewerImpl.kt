package com.donglab.screennameviewer.internal.viewer

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.donglab.screennameviewer.publicapi.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.publicapi.setting.ScreenNameViewerSetting
import com.donglab.screennameviewer.internal.overlay.renderer.ScreenNameOverlayRenderer
import java.lang.ref.WeakReference

internal class ComponentNameViewerImpl(
    private val activityRef: WeakReference<ComponentActivity>,
    private val config: ScreenNameOverlayConfig,
    private val settings: ScreenNameViewerSetting
) : ComponentNameViewer {

    init {
        require(settings.isDebugMode) {
            "ComponentNameViewer should only be used in debug builds"
        }

        activity?.lifecycle?.addObserver(ActivityLifecycleObserver())
    }

    private val activity: ComponentActivity?
        get() = activityRef.get()

    private val overlayRenderer: ScreenNameOverlayRenderer by lazy {
        ScreenNameOverlayRenderer(activityRef, config)
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