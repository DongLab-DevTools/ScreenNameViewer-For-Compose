package com.donglab.screennameviewer

import android.app.Application
import com.donglab.screennameviewer.publicapi.initScreenNameViewer

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initScreenNameViewer(this) {
            settings {
                debugMode { true }
                enabled { true }
            }
        }
    }
}