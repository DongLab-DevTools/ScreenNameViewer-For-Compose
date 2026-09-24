package com.donglab.screennameviewer.internal.overlay.builder

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.text.TextUtils
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.donglab.screennameviewer.internal.util.dp
import com.donglab.screennameviewer.publicapi.config.ScreenNameOverlayConfig

/**
 * 라벨 TextView 를 생성합니다. 라벨마다 둥근 반투명 배경(알약 모양)을 가집니다.
 * 글자색·크기만 라벨 종류(route 등)별로 달리 줄 수 있습니다.
 */
internal class StyledTextViewBuilder(
    private val config: ScreenNameOverlayConfig,
    private val labelTextColor: Int = config.textColor,
    private val labelTextSize: Float = config.textSize,
) {

    @SuppressLint("ClickableViewAccessibility")
    fun build(context: Context, text: String): TextView {
        return TextView(context).apply {
            this.text = text
            this.tag = text

            // TextView 의 textSize(px) 와 이름이 겹치지 않도록 label 접두사 사용
            setTextColor(labelTextColor)
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, labelTextSize)
            typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
            setSingleLine()
            ellipsize = TextUtils.TruncateAt.MIDDLE
            background = createBackground()
            setPadding(config.paddingHorizontal, config.paddingVertical, config.paddingHorizontal, config.paddingVertical)
            layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                bottomMargin = LABEL_SPACING.dp
            }

            setOnTouchListener { _, _ ->
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                parent?.requestDisallowInterceptTouchEvent(false)
                false
            }
        }
    }

    private fun createBackground() = GradientDrawable().apply {
        setColor(config.backgroundColor)
        cornerRadius = config.cornerRadius.dp.toFloat()
    }

    private companion object {
        const val WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT

        /** 라벨 사이 세로 간격 (dp) */
        const val LABEL_SPACING = 2
    }
}
