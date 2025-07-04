# Android App

Android 앱 프로젝트입니다.

## 환경 요구사항

- Android Studio Hedgehog 이상 (2023.1.1)
- JDK 11 이상
- Android SDK API 24 이상
- Android Gradle Plugin 8.10.1
- Gradle 8.9

## 설정 방법

1. **프로젝트 클론**

   ```bash
   git clone <repository-url>
   cd app
   ```

2. **Android Studio에서 프로젝트 열기**

   - Android Studio를 실행합니다
   - `Open an existing Android Studio project`를 선택합니다
   - 클론한 프로젝트 폴더를 선택합니다

3. **Gradle 동기화**

   - 프로젝트가 열리면 Android Studio에서 자동으로 Gradle 동기화가 시작됩니다
   - 만약 자동으로 시작되지 않으면 `File > Sync Project with Gradle Files`를 클릭합니다

4. **SDK 설정**
   - `File > Project Structure`를 열고 SDK 경로가 올바른지 확인합니다
   - 필요한 SDK 버전이 설치되어 있는지 확인합니다

## 실행 방법

1. **디바이스 연결 또는 에뮬레이터 실행**

   - 실제 Android 디바이스를 USB로 연결하거나
   - Android Studio의 AVD Manager를 사용하여 에뮬레이터를 실행합니다

2. **앱 실행**
   - 툴바에서 `app` 실행 구성을 선택합니다
   - 재생 버튼(▶)을 클릭하거나 `Shift + F10`을 누릅니다

## 빌드 정보

- **Target SDK**: 36
- **Min SDK**: 24
- **Compile SDK**: 36
- **Java Version**: 11
- **Kotlin**: 최신 버전
- **Compose**: 활성화됨

## 문제 해결

### Gradle 동기화 오류

```bash
./gradlew clean
./gradlew build
```

### JDK 버전 오류

`File > Project Structure > SDK Location`에서 JDK 경로를 확인하고 올바른 버전(JDK 11 이상)을 선택합니다.

### 의존성 오류

```bash
./gradlew build --refresh-dependencies
```

## 기여하기

1. 이 저장소를 포크합니다
2. 새 기능 브랜치를 생성합니다 (`git checkout -b feature/새기능`)
3. 변경사항을 커밋합니다 (`git commit -am '새 기능 추가'`)
4. 브랜치에 푸시합니다 (`git push origin feature/새기능`)
5. Pull Request를 생성합니다
