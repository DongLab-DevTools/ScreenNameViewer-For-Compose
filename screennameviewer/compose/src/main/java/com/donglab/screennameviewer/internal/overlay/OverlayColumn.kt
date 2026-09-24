package com.donglab.screennameviewer.internal.overlay

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.children
import com.donglab.screennameviewer.internal.util.dp

/**
 * 같은 gravity 의 라벨 묶음들을 겹치지 않게 세로로 쌓는 decorView 공용 컬럼입니다.
 * gravity 별로 하나만 만들고, 마지막 묶음이 빠지면 제거합니다.
 */
internal object OverlayColumn {

    /** 컬럼 안에서 위 -> 아래 순서 */
    enum class Slot { COMPOSE_ROUTE, FRAGMENT }

    fun attach(decorView: ViewGroup, group: LinearLayout, slot: Slot, gravity: Int, topMargin: Int) {
        val column = findOrCreateColumn(decorView, gravity, topMargin)
        val index = column.children.count { child -> (child.tag as? Slot)?.let { it < slot } == true }

        group.tag = slot
        group.gravity = gravity // 묶음 안 라벨들도 같은 쪽으로 정렬 (라벨별 개별 박스)
        column.addView(group, index, LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT))
    }

    fun detach(group: View) {
        val column = group.parent as? ViewGroup ?: return
        column.removeView(group)

        if (column.childCount == 0) {
            (column.parent as? ViewGroup)?.removeView(column)
        }
    }

    private fun findOrCreateColumn(decorView: ViewGroup, gravity: Int, topMargin: Int): LinearLayout {
        val columnTag = "ScreenNameViewer_Column_${Gravity.getAbsoluteGravity(gravity, decorView.layoutDirection)}"
        val existing = decorView.children.firstOrNull { it.tag == columnTag } as? LinearLayout

        return existing ?: LinearLayout(decorView.context).apply {
            orientation = LinearLayout.VERTICAL
            tag = columnTag
            this.gravity = gravity // 컬럼 안 묶음들도 같은 쪽으로 정렬
            decorView.addView(
                this,
                FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).also {
                    it.gravity = gravity
                    it.topMargin = topMargin
                    it.leftMargin = EDGE_MARGIN.dp
                    it.rightMargin = EDGE_MARGIN.dp
                },
            )
        }
    }

    private const val WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT

    /** 화면 좌우 가장자리와 라벨 사이 여백 (dp) */
    const val EDGE_MARGIN = 8
}
