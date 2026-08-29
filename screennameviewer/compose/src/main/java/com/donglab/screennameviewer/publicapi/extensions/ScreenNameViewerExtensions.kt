package com.donglab.screennameviewer.publicapi.extensions

import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.donglab.screennameviewer.internal.compose.tracker.ComposeScreenNameTracker
import com.donglab.screennameviewer.internal.compose.viewer.ComposeRouteViewerImpl
import com.donglab.screennameviewer.internal.util.findActivityContext
import com.donglab.screennameviewer.internal.util.safely
import com.donglab.screennameviewer.publicapi.viewer.ScreenNameViewer

/**
 * ComposeScreenNameTracker를 활성화하는 Composable 함수입니다.
 *
 * @param navController 디버깅할 NavController
 * @param content 하위 Composable 콘텐츠
 */
@Composable
fun ScreenNameTracker(
    navController: NavController,
    content: @Composable () -> Unit
) {
    content()

    if (ScreenNameViewer.settings.isEnabled.not()) return

    val context = LocalContext.current
    val activity = context.findActivityContext() as? ComponentActivity ?: return
    val decorView = activity.window?.decorView as? ViewGroup ?: return

    DisposableEffect(navController, activity) {
        val screenNameViewer = ComposeScreenNameTracker(
            navController = navController,
            composeRouteViewer = ComposeRouteViewerImpl(
                context = activity,
                decorView = decorView,
                config = ScreenNameViewer.config,
            )
        )

        onDispose {
            screenNameViewer.cleanup()
        }
    }
}

/**
 * NavController 없이 현재 화면명만으로 오버레이를 갱신하는 오버로드입니다.
 * Navigation3(NavDisplay) 등 NavController 가 없는 네비게이션에서 사용합니다.
 *
 * 화면명은 composition 단계가 아닌 snapshotFlow(coroutine) 에서만 읽으므로,
 * route 변경이 content 를 리컴포지션시키지 않습니다.
 *
 * @param currentRoute 현재 최상단 화면명 provider — Compose State 를 읽어야 변화가 추적됩니다 (null/blank 는 미표시)
 * @param content 하위 Composable 콘텐츠
 */
@Composable
fun ScreenNameTracker(
    currentRoute: () -> String?,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val activity = context.findActivityContext() as? ComponentActivity
    val decorView = activity?.window?.decorView as? ViewGroup
    val latestRoute = rememberUpdatedState(currentRoute)

    // 뷰어 생성은 라이브러리가 host 의 composition 에서 실행하는 유일한 지점이므로,
    // 생성 실패가 content()(앱 UI) 를 막지 못하도록 여기서 격리한다.
    // addRoute/removeRoute/clear 의 실패 격리는 ComposeRouteViewerImpl 내부가 담당한다.
    val viewer = remember(activity, decorView) {
        if (ScreenNameViewer.settings.isEnabled && activity != null && decorView != null) {
            runCatching { ComposeRouteViewerImpl(activity, decorView, ScreenNameViewer.config) }.getOrNull()
        } else {
            null
        }
    }

    if (viewer != null) {
        DisposableEffect(viewer) {
            onDispose { viewer.clear() }
        }

        LaunchedEffect(viewer) {
            // 앱이 넘긴 currentRoute() 는 뷰 메서드가 아니므로 여기서만 격리한다.
            safely {
                var last: String? = null
                snapshotFlow { latestRoute.value()?.takeIf { it.isNotBlank() } }
                    .collect { next ->
                        last?.let(viewer::removeRoute)
                        next?.let(viewer::addRoute)
                        last = next
                    }
            }
        }
    }

    content()
}
