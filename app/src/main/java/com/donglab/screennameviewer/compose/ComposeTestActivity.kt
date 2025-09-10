package com.donglab.screennameviewer.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.donglab.screennameviewer.config.ScreenNameOverlayConfig
import com.donglab.screennameviewer.config.ScreenNameViewerSetting
import com.donglab.screennameviewer.factory.ScreenNameViewerFactory
import com.donglab.screennameviewer.compose.sample.screens.DetailScreen
import com.donglab.screennameviewer.compose.sample.screens.HomeScreen
import com.donglab.screennameviewer.compose.sample.screens.ProfileScreen
import com.donglab.screennameviewer.compose.sample.screens.SettingsScreen
import com.donglab.screennameviewer.compose.sample.screens.dialogs.AboutDialog
import com.donglab.screennameviewer.compose.sample.screens.dialogs.EditProfileDialog
import com.donglab.screennameviewer.compose.sample.screens.nested.NestedScreen
import com.donglab.screennameviewer.compose.sample.screens.tabs.TabScreen

/**
 * Comprehensive test Activity for the enhanced Compose screen tracking system.
 * 
 * This Activity demonstrates various Compose scenarios:
 * - Navigation Compose with multiple destinations
 * - Parameterized routes (detail/{id})
 * - Dialog composables
 * - Bottom sheet composables
 * - Nested navigation structures
 * - Tab navigation
 * 
 * Expected behavior:
 * - Home route → "HomeScreen" displayed in overlay
 * - Detail route → "DetailScreen" displayed in overlay
 * - Profile route → "ProfileScreen" displayed in overlay
 * - Settings route → "SettingsScreen" displayed in overlay
 * - Dialog opened → "EditProfileDialog" or "AboutDialog" displayed
 * - Bottom sheet opened → "PreferencesBottomSheet" displayed
 * - Navigation changes should update overlay in real-time
 */
class ComposeTestActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            ComposeTestApp()
        }
    }
}

@androidx.compose.runtime.Composable
private fun ComposeTestApp() {
    val navController = rememberNavController()
    
    val context = LocalContext.current
    
    // Navigation Screen Tracker 설정
    DisposableEffect(navController) {
        val tracker = ScreenNameViewerFactory.createForCompose(
            activity = context as ComponentActivity,
            navController = navController
        )

        onDispose {
            tracker.cleanup()
        }
    }
    
    // Dialog state management
    var showEditDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    
    // Navigation Host
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        // Home Screen
        composable("home") {
            HomeScreen(
                onNavigateToDetail = { id ->
                    navController.navigate("detail/$id")
                },
                onNavigateToProfile = {
                    navController.navigate("profile")
                },
                onNavigateToSettings = {
                    navController.navigate("settings")
                },
                onOpenDialog = {
                    showEditDialog = true
                }
            )
        }
        
        // Detail Screen with parameter
        composable(
            route = "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val detailId = backStackEntry.arguments?.getInt("id") ?: 0
            
            DetailScreen(
                detailId = detailId,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToNested = {
                    navController.navigate("nested")
                }
            )
        }
        
        // Profile Screen
        composable("profile") {
            ProfileScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onOpenEditDialog = {
                    showEditDialog = true
                }
            )
        }
        
        // Settings Screen
        composable("settings") {
            SettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onOpenAboutDialog = {
                    showAboutDialog = true
                }
            )
        }
        
        // Nested Screen
        composable("nested") {
            NestedScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToTab = {
                    navController.navigate("tab")
                }
            )
        }
        
        // Tab Screen
        composable("tab") {
            TabScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
    
    // Dialog Composables
    if (showEditDialog) {
        EditProfileDialog(
            onDismiss = { showEditDialog = false },
            onSave = {
                // Handle save logic
            }
        )
    }
    
    if (showAboutDialog) {
        AboutDialog(
            onDismiss = { showAboutDialog = false }
        )
    }
    
}