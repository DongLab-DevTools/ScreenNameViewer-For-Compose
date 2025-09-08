package com.donglab.screennameviewer.lifecycle

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import com.donglab.screennameviewer.config.ClassNameDebugViewerConfig
import com.donglab.screennameviewer.config.ClassNameViewerSettings
import com.donglab.screennameviewer.factory.ClassNameViewerFactory
import com.donglab.screennameviewer.viewer.ClassNameDebugViewer

class ClassNameDebugLifecycleHandler(
    private val settings: ClassNameViewerSettings,
    private val config: ClassNameDebugViewerConfig = ClassNameDebugViewerConfig.defaultConfig()
) : ActivityLifecycleCallbacks {

    init {
        require(settings.isDebugMode) {
            "ClassNameDebugLifecycleHandler should only be used in debug builds"
        }
    }

    // Activity ID별로 debugViewer 저장
    private val debugViewers = mutableMapOf<String, ClassNameDebugViewer>()
    
    // Activity 고유 ID 생성
    private fun getActivityId(activity: Activity): String {
        return "${activity.javaClass.simpleName}_${activity.hashCode()}"
    }

    private fun createFragmentCallback(activityId: String): FragmentLifecycleCallbacks {
        return object : FragmentLifecycleCallbacks() {
            override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
                debugViewers[activityId]?.registerFragment(f)
            }
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity !is ComponentActivity) return

        val activityId = getActivityId(activity)
        if (debugViewers.containsKey(activityId)) return

        // 팩토리를 통해 Viewer 인스턴스 생성
        ClassNameViewerFactory.create(activity, settings, config).apply {
            initialize()
            debugViewers[activityId] = this
        }

        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                createFragmentCallback(activityId),
                true
            )
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
        val activityId = getActivityId(activity)
        debugViewers.remove(activityId)?.clear()
    }

    override fun onActivityStarted(activity: Activity) = Unit
    override fun onActivityResumed(activity: Activity) = Unit
    override fun onActivityPaused(activity: Activity) = Unit
    override fun onActivityStopped(activity: Activity) = Unit
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit
}