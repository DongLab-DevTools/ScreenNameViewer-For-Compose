package com.donglab.screennameviewer.compose.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.donglab.screennameviewer.compose.ui.theme.ScreenNameViewerForComposeTheme
import com.donglab.screennameviewer.publicapi.extensions.ScreenNameTracker

/**
 * androidx.navigation3 NavDisplay 로 [ScreenNameTracker] 의 currentRoute 오버로드를 검증하는 샘플입니다.
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

private sealed interface Nav3Key : NavKey {
    data object Dashboard : Nav3Key
    data object Notifications : Nav3Key
    data object Favorites : Nav3Key
    data object Account : Nav3Key
}

private data class Nav3Tab(val key: Nav3Key, val label: String, val icon: ImageVector)

private val nav3Tabs = listOf(
    Nav3Tab(Nav3Key.Dashboard, "대시보드", Icons.Filled.Home),
    Nav3Tab(Nav3Key.Notifications, "알림", Icons.Filled.Notifications),
    Nav3Tab(Nav3Key.Favorites, "즐겨찾기", Icons.Filled.Favorite),
    Nav3Tab(Nav3Key.Account, "계정", Icons.Filled.AccountCircle),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Navigation3SampleApp() {
    val backStack = remember { mutableStateListOf<Nav3Key>(Nav3Key.Dashboard) }
    val current = backStack.last()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Navigation3 (NavDisplay) 샘플") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
            )
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                nav3Tabs.forEach { tab ->
                    NavigationBarItem(
                        icon = { Icon(tab.icon, contentDescription = tab.label) },
                        label = { Text(tab.label) },
                        selected = current == tab.key,
                        onClick = {
                            if (current != tab.key) {
                                backStack.clear()
                                backStack.add(tab.key)
                            }
                        },
                    )
                }
            }
        },
    ) { innerPadding ->
        // NavController 없이 백스택 top 화면명을 provider 로 전달
        ScreenNameTracker(currentRoute = { backStack.lastOrNull()?.let { it::class.simpleName } }) {
            NavDisplay(
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                entryProvider = entryProvider {
                    entry<Nav3Key.Dashboard> { ComposeDashboardScreen() }
                    entry<Nav3Key.Notifications> { ComposeNotificationsScreen() }
                    entry<Nav3Key.Favorites> { ComposeFavoritesScreen() }
                    entry<Nav3Key.Account> { ComposeAccountScreen() }
                },
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}
