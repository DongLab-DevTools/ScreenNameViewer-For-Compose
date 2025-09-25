package com.donglab.screennameviewer

import android.app.Application

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ScreenNameViewerInitializer.init(this)
    }
}