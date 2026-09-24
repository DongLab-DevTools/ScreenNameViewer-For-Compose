package com.donglab.screennameviewer.internal.viewer

import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.donglab.screennameviewer.publicapi.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.publicapi.setting.ScreenNameViewerSetting
import com.donglab.screennameviewer.internal.overlay.renderer.ScreenNameOverlayRenderer
import com.donglab.screennameviewer.internal.util.safely
import java.lang.ref.WeakReference

internal class ComponentNameViewerImpl(
    private val activityRef: WeakReference<ComponentActivity>,
    private val config: ScreenNameOverlayConfig,
) : ComponentNameViewer {

    init {
        activity?.lifecycle?.addObserver(ActivityLifecycleObserver())
    }

    private val activity: ComponentActivity?
        get() = activityRef.get()

    private val overlayRenderer: ScreenNameOverlayRenderer by lazy {
        ScreenNameOverlayRenderer(activityRef, config)
    }

    override fun registerFragment(fragment: Fragment) {
        val viewLifecycle = fragment.viewLifecycleOwner.lifecycle
        viewLifecycle.addObserver(FragmentLifecycleObserver(fragment, viewLifecycle))
    }

    override fun clear() {
        overlayRenderer.clearOverlay()
    }

    private inner class ActivityLifecycleObserver : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) = safely {
            overlayRenderer.addActivityName(activity?.javaClass?.simpleName ?: "Unknown Activity")
        }

        override fun onDestroy(owner: LifecycleOwner) = safely {
            clear()
        }
    }

    private inner class FragmentLifecycleObserver(
        private val fragment: Fragment,
        private val viewLifecycle: Lifecycle,
    ) : DefaultLifecycleObserver {
        private val fragmentName = fragment.javaClass.simpleName

        // hide()/show() 는 lifecycle 콜백이 없어 레이아웃 변화 시점에 다시 판단
        private val visibilityListener = ViewTreeObserver.OnGlobalLayoutListener { safely { syncLabel() } }

        override fun onCreate(owner: LifecycleOwner) = safely {
            activity?.window?.decorView?.viewTreeObserver?.addOnGlobalLayoutListener(visibilityListener)
        }

        override fun onResume(owner: LifecycleOwner) = safely {
            syncLabel()
        }

        override fun onPause(owner: LifecycleOwner) = safely {
            syncLabel()
        }

        override fun onDestroy(owner: LifecycleOwner) = safely {
            activity?.window?.decorView?.viewTreeObserver?.removeOnGlobalLayoutListener(visibilityListener)
            if (fragment !is DialogFragment) return@safely
            overlayRenderer.removeFragmentName(fragmentName)
        }

        /**
         * RESUMED 이고 자신·부모 Fragment 가 모두 hidden 이 아닐 때만 라벨을 표시합니다.
         */
        private fun syncLabel() {
            val isResumed = viewLifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)
            val isDisplayed = generateSequence(fragment) { it.parentFragment }.none { it.isHidden }

            if (isResumed && isDisplayed) {
                overlayRenderer.addFragmentName(fragmentName)
            } else {
                overlayRenderer.removeFragmentName(fragmentName)
            }
        }
    }
}