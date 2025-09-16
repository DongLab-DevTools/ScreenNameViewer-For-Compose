package com.donglab.screennameviewer.compose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.donglab.screennameviewer.compose.ui.theme.ScreenNameViewerForComposeTheme
import com.donglab.screennameviewer.compose.sample.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScreenNameViewerForComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SampleSelectionScreen(
                        onViewPagerSampleClick = {
                            startActivity(Intent(this@MainActivity, ViewPagerSampleActivity::class.java))
                        },
                        onShowHideSampleClick = {
                            startActivity(Intent(this@MainActivity, ShowHideFragmentActivity::class.java))
                        },
                        onComposeSampleClick = {
                            startActivity(Intent(this@MainActivity, ComposeSampleActivity::class.java))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SampleSelectionScreen(
    onViewPagerSampleClick: () -> Unit,
    onShowHideSampleClick: () -> Unit,
    onComposeSampleClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = "샘플앱 선택",
            style = MaterialTheme.typography.headlineMedium
        )

        Button(
            onClick = onViewPagerSampleClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("XML ViewPager Fragment 샘플")
        }

        Button(
            onClick = onShowHideSampleClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("XML Show/Hide Fragment 샘플")
        }

        Button(
            onClick = onComposeSampleClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Compose 다중 화면 샘플")
        }
    }
}