package com.donglab.screennameviewer.internal.overlay

import android.view.Gravity

/**
 * 두 오버레이가 같은 위치(같은 좌우 + 둘 다 TOP)에 배치되는지 판단합니다.
 * START/END 와 LEFT/RIGHT 가 섞여 있어도 layoutDirection 기준으로 비교합니다.
 */
internal fun isSameOverlayPosition(gravityA: Int, gravityB: Int, layoutDirection: Int): Boolean {
    val horizontalA = Gravity.getAbsoluteGravity(gravityA, layoutDirection) and Gravity.HORIZONTAL_GRAVITY_MASK
    val horizontalB = Gravity.getAbsoluteGravity(gravityB, layoutDirection) and Gravity.HORIZONTAL_GRAVITY_MASK
    val isBothTop = gravityA and Gravity.VERTICAL_GRAVITY_MASK == Gravity.TOP &&
        gravityB and Gravity.VERTICAL_GRAVITY_MASK == Gravity.TOP

    return horizontalA == horizontalB && isBothTop
}
