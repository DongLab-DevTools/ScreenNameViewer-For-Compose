package com.donglab.screennameviewer.compose.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.donglab.screennameviewer.compose.ui.theme.ScreenNameViewerForComposeTheme
import com.donglab.screennameviewer.publicapi.extensions.ScreenNameTracker

/**
 * NavController 가 없는 Navigation3 스타일 샘플.
 *
 * 화면 키 백스택을 앱이 직접 관리하고(= NavDisplay 의 entries 모델),
 * NavController 대신 현재 화면명 provider 를 넘기는 신규 [ScreenNameTracker] 오버로드를 사용한다.
 */
class Navigation3SampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScreenNameViewerForComposeTheme {
                Navigation3SampleApp()
            }
        }
    }
}

private enum class Nav3Screen(val label: String, val icon: ImageVector) {
    Dashboard("대시보드", Icons.Filled.Home),
    Notifications("알림", Icons.Filled.Notifications),
    Favorites("즐겨찾기", Icons.Filled.Favorite),
    Account("계정", Icons.Filled.AccountCircle),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Navigation3SampleApp() {
    // Navigation3 모델: 화면 키 백스택을 앱이 직접 소유한다.
    val backStack = remember { mutableStateListOf(Nav3Screen.Dashboard) }
    val current = backStack.last()

    BackHandler(enabled = backStack.size > 1) {
        backStack.removeAt(backStack.lastIndex)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Navigation3 (NavController 없음) 샘플") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
            )
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                Nav3Screen.entries.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        label = { Text(screen.label) },
                        selected = current == screen,
                        onClick = { backStack.switchTab(screen) },
                    )
                }
            }
        },
    ) { innerPadding ->
        // 신규 오버로드: NavController 없이 현재 화면명만 제공.
        // 화면 전환 시 오버레이 라벨이 백스택 top 을 따라 갱신된다.
        ScreenNameTracker(currentRoute = { backStack.last().name }) {
            Box(modifier = Modifier.padding(innerPadding)) {
                when (current) {
                    Nav3Screen.Dashboard -> ComposeDashboardScreen()
                    Nav3Screen.Notifications -> ComposeNotificationsScreen()
                    Nav3Screen.Favorites -> ComposeFavoritesScreen()
                    Nav3Screen.Account -> ComposeAccountScreen()
                }
            }
        }
    }
}

/** 탭 이동: 이미 있으면 그 위 항목을 걷어내고, 없으면 top 에 쌓는다 (single-top 유사). */
private fun MutableList<Nav3Screen>.switchTab(target: Nav3Screen) {
    val index = indexOf(target)
    if (index >= 0) {
        while (lastIndex > index) removeAt(lastIndex)
    } else {
        add(target)
    }
}
