package com.donglab.screennameviewer.compose.sample.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.donglab.screennameviewer.compose.R
import com.donglab.screennameviewer.publicapi.extensions.ScreenNameTracker

/**
 * XML Fragment 내에 Compose 영역이 포함된 하이브리드 Fragment
 * Fragment: "ComposeWithinFragment"
 * Routes: "hybrid_screen1", "hybrid_screen2", "hybrid_screen3"
 */
class ComposeWithinFragment : Fragment() {
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_compose_within, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // XML 부분 설정
        val headerText = view.findViewById<TextView>(R.id.headerText)
        headerText.text = "🔄 Hybrid Fragment (XML + Compose)"
        
        // Compose 부분 설정
        val composeView = view.findViewById<ComposeView>(R.id.composeView)
        composeView.setContent {
            HybridComposeContent()
        }
    }
}

@Composable
private fun HybridComposeContent() {
    val navController = rememberNavController()

    ScreenNameTracker(navController = navController) {
        NavHost(
            navController = navController,
            startDestination = "hybrid_screen1"
        ) {
            composable("hybrid_screen1") {
                HybridScreen1(
                    onNavigateToScreen2 = {
                        navController.navigate("hybrid_screen2")
                    }
                )
            }

            composable("hybrid_screen2") {
                HybridScreen2(
                    onNavigateToScreen3 = {
                        navController.navigate("hybrid_screen3")
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable("hybrid_screen3") {
                HybridScreen3(
                    onNavigateToScreen1 = {
                        navController.navigate("hybrid_screen1") {
                            popUpTo("hybrid_screen1") { inclusive = true }
                        }
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

@Composable
private fun HybridScreen1(
    onNavigateToScreen2: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
    ) {
        BasicText(text = "🎯 Hybrid Screen 1")
        BasicText(text = "Fragment: ComposeWithinFragment")
        BasicText(text = "Route: hybrid_screen1")
        BasicText(text = "이 영역은 XML Fragment 내의 Compose 영역입니다.")
        
        BasicText(
            text = "→ Go to Hybrid Screen 2",
            modifier = Modifier.clickable { onNavigateToScreen2() }
        )
    }
}

@Composable
private fun HybridScreen2(
    onNavigateToScreen3: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
    ) {
        BasicText(text = "🎯 Hybrid Screen 2")
        BasicText(text = "Fragment: ComposeWithinFragment")
        BasicText(text = "Route: hybrid_screen2")
        BasicText(text = "XML과 Compose가 혼합된 구조에서도 정상 추적")
        
        BasicText(
            text = "→ Go to Hybrid Screen 3",
            modifier = Modifier.clickable { onNavigateToScreen3() }
        )
        
        BasicText(
            text = "← Back to Screen 1",
            modifier = Modifier.clickable { onNavigateBack() }
        )
    }
}

@Composable
private fun HybridScreen3(
    onNavigateToScreen1: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
    ) {
        BasicText(text = "🎯 Hybrid Screen 3")
        BasicText(text = "Fragment: ComposeWithinFragment")
        BasicText(text = "Route: hybrid_screen3")
        BasicText(text = "마지막 하이브리드 화면입니다.")
        
        BasicText(
            text = "🔄 Reset to Screen 1",
            modifier = Modifier.clickable { onNavigateToScreen1() }
        )
        
        BasicText(
            text = "← Back to Screen 2",
            modifier = Modifier.clickable { onNavigateBack() }
        )
    }
}