# ✈️ TrippiNote

> ### **Trip it, Note it! : TrippiNote**

> 여행을 계획하고, 추억을 남기는 기록 앱  
> _(Tripping + Note = TrippiNote)_

<br>

## 🧑‍💻 팀원 소개

### 권민성

- 성균관대학교 소프트웨어학과
- [Github](https://github.com/kweonminsung) `@kweonminsung`

### 이지은

- KAIST 전산학부
- [Github](https://github.com/jien040708) `@jien040708`

<br>

## ✅ TrippiNote는 이런 분들을 위한 앱입니다!

- ✈️ 여행을 자주 다니는 사람
- 📸 여행 기록을 남기고 싶은 사람
- 🗂️ 여행을 체계적으로 준비하고 싶은 사람

<br>

## 🛠 기술 스택

- **언어 & UI 프레임워크**: Kotlin, Jetpack Compose
- **외부 API 연동**: Google Maps API
- **데이터 저장소**:
  - 로컬 데이터베이스: SQLite
  - 커스텀 구현 Key-Value 저장소
  - 커스텀 객체 저장소 (사진 및 구조화된 데이터 관리용)

<br>

## 📱 주요 기능 소개

### 🗂️ Tab 1: 나의 여행 리스트

- 여행 추가 및 삭제
- 가까운 일정 자동 정렬
- 여행별 썸네일, 리스트 확인

### 📸 Tab 2: 여행 사진 업로드

- 여행/지역/일정 기반 폴더 생성
- 일정 폴더에 사진 업로드 및 조회

### 🗺️ Tab 3: 나의 여행 지도

- 지역, 일정 위치 지도에서 확인
- 검색 기능으로 장소/일정 빠르게 찾기
- 상세 일정 이동 수단 및 소요 시간 표시

### 👤 Tab 4: 나의 프로필

- 여권번호 등 여행 정보 관리
- 여행 횟수 및 지역 수 통계
- 여행 전 체크리스트 관리 기능

## 📦 APK 다운로드

👉 [**Release v0.1.0**](https://github.com/kweonminsung/TrippiNote/releases/tag/v0.1.0) 에서 APK를 다운로드해 직접 사용해보세요!

## System Requirements

| Item                  | Version                       |
| --------------------- | ----------------------------- |
| Android Studio        | Hedgehog or higher (2023.1.1) |
| JDK                   | 11 or higher                  |
| Android SDK           | API 24 or higher              |
| Android Gradle Plugin | 8.1.0                         |
| Gradle                | 8.9                           |

## Setup Instructions

### 1. Clone the Project

```bash
git clone https://github.com/kweonminsung/TrippiNote.git
```

### 2. Open in Android Studio

1. Launch **Android Studio**
2. Select **Open an existing Android Studio project**
3. Choose the cloned project directory

### 3. Sync Gradle

- Gradle will sync automatically when the project opens
- If not, go to `File > Sync Project with Gradle Files`

### 4. Check SDK Configuration

- Open `File > Project Structure`
- Make sure the SDK path is correct and the required versions are installed

## How to Run

### 1. Connect a Device or Launch an Emulator

- Connect a real Android device via USB
- Or start a virtual device using **AVD Manager**

### 2. Run the App

- Select the `app` run configuration from the toolbar
- Click the ▶️ **Run** button or press `Shift + F10`

## Build Info

- **Target SDK**: 36
- **Min SDK**: 24
- **Compile SDK**: 36
- **Java Version**: 11
- **Kotlin**: Latest version
- **Jetpack Compose**: Enabled
