package com.donglab.classnameviewer.sample.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.donglab.screennameviewer.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.config.ScreenNameViewerSetting
import com.donglab.screennameviewer.factory.ScreenNameViewerFactory

/**
 * 순수 Compose로 구성된 Fragment (Navigation 포함)
 * Fragment: "PureComposeFragment"
 * Routes: "fragment_home", "fragment_detail", "fragment_settings"
 */
class PureComposeFragment : Fragment() {
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(activity).apply {
            setContent {
                PureComposeContent()
            }
        }
    }
}

@Composable
private fun PureComposeContent() {
    val navController = rememberNavController()
    val context = LocalContext.current
    
    // Navigation Screen Tracker 설정 (Fragment 내에서)
    DisposableEffect(navController) {
        val tracker = ScreenNameViewerFactory.createForCompose(
            activity = context as ComponentActivity,
            navController = navController,
            settings = ScreenNameViewerSetting(
                debugModeCondition = { true },
                enabledCondition = { true }
            ),
            config = ScreenNameOverlayConfig.defaultConfig()
        )
        
        onDispose {
            tracker.cleanup()
        }
    }
    
    NavHost(
        navController = navController,
        startDestination = "fragment_home"
    ) {
        composable("fragment_home") {
            FragmentHomeScreen(
                onNavigateToDetail = {
                    navController.navigate("fragment_detail")
                },
                onNavigateToSettings = {
                    navController.navigate("fragment_settings")
                }
            )
        }
        
        composable("fragment_detail") {
            FragmentDetailScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("fragment_settings") {
            FragmentSettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
private fun FragmentHomeScreen(
    onNavigateToDetail: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        BasicText(text = "🧩 Pure Compose Fragment")
        BasicText(text = "Fragment: PureComposeFragment")
        BasicText(text = "Current Route: fragment_home")
        
        BasicText(
            text = "→ Go to Fragment Detail",
            modifier = Modifier.clickable { onNavigateToDetail() }
        )
        
        BasicText(
            text = "→ Go to Fragment Settings",
            modifier = Modifier.clickable { onNavigateToSettings() }
        )
    }
}

@Composable
private fun FragmentDetailScreen(
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        BasicText(text = "📋 Fragment Detail Screen")
        BasicText(text = "Fragment: PureComposeFragment")
        BasicText(text = "Current Route: fragment_detail")
        
        BasicText(
            text = "← Back to Fragment Home",
            modifier = Modifier.clickable { onNavigateBack() }
        )
    }
}

@Composable
private fun FragmentSettingsScreen(
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        BasicText(text = "⚙️ Fragment Settings Screen")
        BasicText(text = "Fragment: PureComposeFragment")
        BasicText(text = "Current Route: fragment_settings")
        
        BasicText(
            text = "← Back to Fragment Home",
            modifier = Modifier.clickable { onNavigateBack() }
        )
    }
}