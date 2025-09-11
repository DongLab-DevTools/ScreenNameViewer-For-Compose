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
<table>
	<tbody>
		<tr>
            <td align="center">
                <a href="https://github.com/dongx0915">
                    <img src="https://avatars.githubusercontent.com/u/63500239?v=4" width="100;" alt="dongx0915"/>
                    <br />
                    <sub><b>Donghyeon Kim</b></sub>
                </a>
            </td>
            <td align="center">
                <a href="https://github.com/murjune">
                    <img src="https://avatars.githubusercontent.com/u/87055456?v=4" width="100;" alt="murjune"/>
                    <br />
                    <sub><b>JUNWON LEE</b></sub>
                </a>
            </td>
		</tr>
	<tbody>
</table>
<!-- readme: collaborators,contributors -end -->
