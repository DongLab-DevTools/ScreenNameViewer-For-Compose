package com.donglab.screennameviewer.internal.overlay.builder

import android.annotation.SuppressLint
import android.content.Context
import android.util.TypedValue
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.setPadding
import com.donglab.screennameviewer.publicapi.config.ScreenNameOverlayConfig

internal class StyledTextViewBuilder(
    private val config: ScreenNameOverlayConfig
) {

    @SuppressLint("ClickableViewAccessibility")
    fun build(context: Context, text: String): TextView {
        return TextView(context).apply {
            this.text = text
            this.tag = text

            with(config) {
                setTextColor(textColor)
                setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize)
                setBackgroundColor(backgroundColor)
                setPadding(padding)
            }

            setOnTouchListener { _, _ ->
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                parent?.requestDisallowInterceptTouchEvent(false)
                false
            }
        }
    }
}