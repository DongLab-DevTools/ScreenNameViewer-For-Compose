package com.donglab.screennameviewer.internal.lifecycle

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import com.donglab.screennameviewer.publicapi.ScreenNameViewerSdk
import com.donglab.screennameviewer.internal.viewer.ScreenNameViewer
import com.donglab.screennameviewer.internal.viewer.ScreenNameViewerImpl
import java.lang.ref.WeakReference
import java.util.WeakHashMap

internal class ScreenNameViewerLifecycleHandler : ActivityLifecycleCallbacks {

    private val configuration = ScreenNameViewerSdk.getInstance()
    // Activity ID별로 debugViewer 저장
    private val debugViewers = WeakHashMap<Activity, ScreenNameViewer>()
    private val fragmentCallbacks = WeakHashMap<Activity, FragmentLifecycleCallbacks>()

    private fun createFragmentCallback(owner: FragmentActivity): FragmentLifecycleCallbacks {
        return object : FragmentLifecycleCallbacks() {
            override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
                debugViewers[owner]?.registerFragment(f)
            }
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity !is ComponentActivity) return
        if (debugViewers.containsKey(activity)) return

        ScreenNameViewerImpl(
            activityRef = WeakReference(activity),
            config = configuration.config,
            settings = configuration.settings
        ).apply {
            initialize()
            debugViewers[activity] = this
        }

        if (activity is FragmentActivity) {
            createFragmentCallback(activity).apply {
                fragmentCallbacks[activity] = this
                activity.supportFragmentManager.registerFragmentLifecycleCallbacks(this, true)
            }
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
        debugViewers.remove(activity)?.clear()

        if (activity is FragmentActivity) {
            fragmentCallbacks.remove(activity)?.let { callback ->
                activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(callback)
            }
        }
    }

    override fun onActivityStarted(activity: Activity) = Unit
    override fun onActivityResumed(activity: Activity) = Unit
    override fun onActivityPaused(activity: Activity) = Unit
    override fun onActivityStopped(activity: Activity) = Unit
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit
}