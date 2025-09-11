# ScreenNameViewer-For-Compose



### 사용법

```kotlin
    CompositionLocalProvider {
        val activity = LocalActivity.current as ComponentActivity

        DisposableEffect(navController) {
            val tracker = ComposeScreenNameViewerFactory.createNavigationTracker(
                activity = activity,
                navController = navController,
                settings = ScreenNameViewerSetting(
                    debugModeCondition = { /* Debug Condition */ },
                    enabledCondition = { /* Enable Condition */ }
                ),
                config = ScreenNameOverlayConfig.defaultConfig().copy(
                    textColor = Color.CYAN
                )
            )

            onDispose {
                // NavController 클린업 작업이 필요한 경우 여기에 추가
                tracker.cleanup()
            }
        }

        NavHost() { /*...*/ }
```

## Contributors

<!-- readme: collaborators,contributors -start -->
<!-- readme: collaborators,contributors -end -->
