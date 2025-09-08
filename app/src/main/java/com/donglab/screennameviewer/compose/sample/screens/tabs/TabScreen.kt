package com.donglab.screennameviewer.compose.sample.screens.tabs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Tab screen for testing tab navigation detection.
 * This should appear as "TabScreen" in the overlay.
 */
@Composable
fun TabScreen(
    onNavigateBack: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Tab bar
        TabBar(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )
        
        // Tab content
        when (selectedTab) {
            0 -> TabContent1()
            1 -> TabContent2()
            2 -> TabContent3()
        }
        
        // Navigation
        BasicText(
            text = "← Back to Nested",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable { onNavigateBack() }
                .padding(16.dp)
        )
    }
}

@Composable
private fun TabBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TabItem(
            title = "Tab 1",
            isSelected = selectedTab == 0,
            onClick = { onTabSelected(0) }
        )
        TabItem(
            title = "Tab 2", 
            isSelected = selectedTab == 1,
            onClick = { onTabSelected(1) }
        )
        TabItem(
            title = "Tab 3",
            isSelected = selectedTab == 2,
            onClick = { onTabSelected(2) }
        )
    }
}

@Composable
private fun TabItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    BasicText(
        text = if (isSelected) "[$title]" else title,
        modifier = Modifier
            .clickable { onClick() }
            .padding(16.dp)
    )
}

@Composable
private fun TabContent1() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BasicText(text = "📝 Tab 1 Content")
        BasicText(text = "This is the first tab.")
        BasicText(text = "Content type: Documents")
    }
}

@Composable
private fun TabContent2() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BasicText(text = "📊 Tab 2 Content")
        BasicText(text = "This is the second tab.")
        BasicText(text = "Content type: Analytics")
    }
}

@Composable
private fun TabContent3() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BasicText(text = "⚙️ Tab 3 Content") 
        BasicText(text = "This is the third tab.")
        BasicText(text = "Content type: Settings")
    }
}