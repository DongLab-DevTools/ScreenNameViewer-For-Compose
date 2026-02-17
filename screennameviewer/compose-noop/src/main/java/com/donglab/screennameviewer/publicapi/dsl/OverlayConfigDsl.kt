package com.donglab.screennameviewer.publicapi.dsl

import com.donglab.screennameviewer.publicapi.config.ScreenNameOverlayConfig

/**
 * Noop implementation - does not create overlay config DSL in release builds.
 */
@DslMarker
annotation class OverlayConfigDsl

@OverlayConfigDsl
class OverlayConfigBuilder {
    fun textStyle(block: TextStyleScope.() -> Unit) {}
    fun background(block: BackgroundScope.() -> Unit) {}
    fun position(block: PositionScope.() -> Unit) {}

    fun build(): ScreenNameOverlayConfig = ScreenNameOverlayConfig()
}

@OverlayConfigDsl
class TextStyleScope {
    var size: Float? = null
    var color: Int? = null
}

@OverlayConfigDsl
class BackgroundScope {
    var color: Int? = null
    var padding: Int? = null
}

@OverlayConfigDsl
class PositionScope {
    var topMargin: Int? = null
    var activity: Int? = null
    var fragment: Int? = null
    var composeRoute: Int? = null
}

internal inline fun overlayConfig(block: OverlayConfigBuilder.() -> Unit): ScreenNameOverlayConfig {
    return ScreenNameOverlayConfig()
}
