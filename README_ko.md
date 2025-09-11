# ScreenNameViewer-For-Compose

개발 중인 안드로이드 앱에서 현재 Activity와 Fragment의 클래스명을 화면에 오버레이로 표시해주는 디버그 라이브러리입니다.

## 개요

기존 Xml용 ScreenNameViewer를 확장하여 Compose의 Screen 이름까지 오버레이로 표시해주는 라이브러리입니다.

Single Activity 구조나 Activity/Fragment 내에서 여러 Screen을 관리하는 경우 유용하게 사용될 수 있습니다.

## 특징

- **실시간 클래스명 표시**: Activity와 Fragment 클래스명을 화면에 실시간 표시
- **자동 생명주기 관리**: Application 레벨에서 모든 Activity와 Fragment를 자동으로 추적
- **디버그 전용**: Release 빌드에서는 자동으로 비활성화되어 안전
- **UI 커스터마이징**: 텍스트 크기, 색상, 위치 등 자유롭게 설정 가능
- **메모리 안전**: WeakReference 사용으로 메모리 누수 방지
- **터치 상호작용**: 오버레이 터치 시 Toast로 전체 클래스명 표시

## 설치

프로젝트에 라이브러리를 추가하세요:

```kotlin
dependencies {
    implementation 'com.donglab:screennameviewer:1.0.0'
}
```

### 요구사항
- Android API 21 (Android 5.0) 이상

## 사용법

### Application 클래스에서 초기화

한 번만 설정하면 모든 Activity와 Fragment가 자동으로 추적됩니다:

```kotlin
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ScreenNameViewer.getInstance().initialize(
            application = this,
            settings = ScreenNameViewerSetting(
                debugModeCondition = { BuildConfig.DEBUG },
                enabledCondition = {
                    PreferenceManager.getDefaultSharedPreferences(this)
                        .getBoolean("debug_overlay_enabled", true)
                },
            ),
            config = ScreenNameOverlayConfig.default()
        )
    }
}
```

### NavHost 클래스에서 초기화 (Compose 적용)

```kotlin
    ScreenNameTracker(navController) {
        NavHost() { /*...*/ }
    }
```

## 설정

### UI 커스터마이징

```kotlin
val config = ScreenNameOverlayConfig(
    textSize = 12f,                              // 텍스트 크기
    textColor = Color.WHITE,                     // 텍스트 색상
    backgroundColor = Color.argb(128, 0, 0, 0),  // 배경색
    padding = 16,                                // 패딩
    topMargin = 64,                              // 상단 여백
    activityGravity = Gravity.TOP or Gravity.START,  // Activity 표시 위치
    fragmentGravity = Gravity.TOP or Gravity.END,    // Fragment 표시 위치
    customLabelGravity = Gravity.TOP or Gravity.END  // 커스텀 라벨 표시 위치
)

val lifecycleHandler = ScreenNameViewerLifecycleHandler(settings, config)
```
화면에 표시될 오버레이의 스타일을 커스텀할 수 있습니다.

### 활성화 조건 주입

```kotlin
val settings = ScreenNameViewerSetting(
    debugModeCondition = { BuildConfig.DEBUG },
    enabledCondition = { 
        PreferenceManager.getDefaultSharedPreferences(this)
            .getBoolean("debug_overlay_enabled", true)
    }
)
```
- `debugModeCondition`: 디버그 모드 조건을 주입합니다.
- `enabledCondition`: 오버레이 기능 활성화 조건을 주입합니다.

## 라이선스

```
MIT License

Copyright (c) 2025 DongLab-DevTools

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
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