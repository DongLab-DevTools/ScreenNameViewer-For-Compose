# ScreenNameViewer-For-Compose

## 개요

![sample](https://github.com/DongLab-DevTools/ScreenNameViewer-For-Compose/blob/eae99cecc086002a6958e12620ec80647c89822f/.github/docs/images/screennameviewer-compose-exmaple.png)

<a href="https://github.com/DongLab-DevTools/ScreenNameViewer">
	<img src="https://github.com/DongLab-DevTools/ScreenNameViewer-For-Compose/blob/a1bedb1a1d026948f4b9b8cdf25e95293aab2cf1/.github/docs/images/screen_name_viewer_link_thumb_xml_kr.png"/>
</a>

<br>
<br>

ScreenNameViewer는 현재 표시 중인 화면의 클래스명을 오버레이로 보여주는 디버깅 도구입니다.  
어떤 화면이 활성화되어 있는지 직관적으로 확인할 수 있으며, Compose 환경에서는 Screen Route까지 함께 표시할 수 있습니다. 

이를 통해 원하는 화면의 코드를 빠르게 찾아 접근할 수 있어 디버깅과 개발 효율을 높여줍니다.

## 특징

- **실시간 클래스명 표시**: Activity와 Fragment 클래스명을 화면에 실시간 표시
- **자동 생명주기 관리**: Application 레벨에서 모든 Activity와 Fragment를 자동으로 추적
- **디버그 전용**: Release 빌드에서는 자동으로 비활성화되어 안전
- **UI 커스터마이징**: 텍스트 크기, 색상, 위치 등 자유롭게 설정 가능
- **메모리 안전**: WeakReference 사용으로 메모리 누수 방지
- **터치 상호작용**: 오버레이 터치 시 Toast로 전체 클래스명 표시

## 설치

### 1단계: GitHub Packages 저장소 추가

프로젝트의 `settings.gradle.kts`에 GitHub Packages 저장소를 추가하세요:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
		maven {
            url = uri("https://maven.pkg.github.com/DongLab-DevTools/ScreenNameViewer-For-Compose")

            credentials {
                username = props.getProperty("github_username")
                password = props.getProperty("github_token")
            }
        }
    }
}
```

### 2단계: 의존성 추가

모듈의 `build.gradle.kts`에 라이브러리를 추가하세요:

```kotlin
dependencies {
    implementation 'com.donglab.devtools:screennameviewer-compose:latestVersion'
}
```

### 3단계: 인증 설정

프로젝트 루트에 `gradle.properties` 파일을 생성하고 GitHub 인증 정보를 추가하세요:

```properties
github_username=YOUR_GITHUB_USERNAME
github_token=YOUR_GITHUB_PERSONAL_ACCESS_TOKEN
```

> [!NOTE]
> GitHub Packages에서 다운로드하려면 `read:packages` 권한이 있는 GitHub Personal Access Token이 필요합니다.

### 요구사항
- Android API 21 (Android 5.0) 이상

## 사용법

### Application 클래스에서 초기화

한 번만 설정하면 모든 Activity와 Fragment가 자동으로 추적됩니다:

```kotlin
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initScreenNameViewer(this) {
            settings {
                debugMode { BuildConfig.DEBUG }
                enabled {
                    PreferenceManager.getDefaultSharedPreferences(this@MyApplication)
                        .getBoolean("debug_overlay_enabled", true)
                }
            }
            config {
                textStyle {
                    size = 12f
                    color = Color.WHITE
                }
                background {
                    color = Color.argb(128, 0, 0, 0)
                    padding = 16
                }
                position {
                    topMargin = 64
                    activity = Gravity.TOP or Gravity.START
                    fragment = Gravity.TOP or Gravity.END
                    composeRoute = Gravity.TOP or Gravity.END
                }
            }
        }
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

### DSL 설정

간단한 DSL(Domain Specific Language)을 사용하여 라이브러리를 설정할 수 있습니다:

```kotlin
initScreenNameViewer(this) {
    settings {
        debugMode { BuildConfig.DEBUG }
        enabled {
            PreferenceManager.getDefaultSharedPreferences(this@MyApplication)
                .getBoolean("debug_overlay_enabled", true)
        }
    }
    config {
        textStyle {
            size = 12f                    // 텍스트 크기
            color = Color.WHITE           // 텍스트 색상
        }
        background {
            color = Color.argb(128, 0, 0, 0)  // 배경색
            padding = 16                      // 패딩
        }
        position {
            topMargin = 64                                    // 상단 여백
            activity = Gravity.TOP or Gravity.START          // Activity 표시 위치
            fragment = Gravity.TOP or Gravity.END            // Fragment 표시 위치
            composeRoute = Gravity.TOP or Gravity.END        // Compose Route 표시 위치
        }
    }
}
```

### 설정 옵션

- **settings**: 활성화 조건 설정
  - `debugMode`: 디버그 모드 조건
  - `enabled`: 오버레이 기능 활성화 조건

- **config**: 오버레이 모양 커스터마이징
  - `textStyle`: 텍스트 크기와 색상
  - `background`: 배경색과 패딩
  - `position`: 여백과 각 컴포넌트의 표시 위치

## 기여자

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
