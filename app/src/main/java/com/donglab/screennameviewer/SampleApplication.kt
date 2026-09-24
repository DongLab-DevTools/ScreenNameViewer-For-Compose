package com.donglab.screennameviewer

import android.app.Application
import android.view.Gravity
import com.donglab.screennameviewer.publicapi.dsl.initScreenNameViewer

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initScreenNameViewer(this) {
            settings {
                debugModeCondition = true
                enableCondition = true
            }

            config {
                position {
                    topMargin = 52
                    activity = Gravity.TOP or Gravity.START
                    fragment = Gravity.TOP or Gravity.END
                    composeRoute = Gravity.TOP or Gravity.END
                }
            }
        }
    }
}