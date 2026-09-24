package com.donglab.screennameviewer.internal.overlay.builder

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

internal class OverlayLayoutBuilder {

    fun addTextView(container: LinearLayout?, textView: TextView) {
        if (!hasTextView(container, textView.tag.toString())) {
            container?.addView(textView)
        }
    }

    fun removeTextView(container: LinearLayout?, name: String) {
        container?.findViewWithTag<View>(name)?.let {
            container.removeView(it)
        }
    }

    fun hasTextView(container: LinearLayout?, name: String): Boolean {
        return container?.findViewWithTag<TextView>(name) != null
    }
}