# Project Proposal
## Cross-Platform Mobile Migration: Porting a Swift Weather App to Android

**Course:** KI-assisted Software Engineering  
**Group:** BB-G1  
**Team:** Dorian Toth, Marvin Aichinger  
**Proposal Deadline:** 10.06.2026

---

## Goal of the Project

### High-Level Goal
The goal of this project is to port an existing iOS weather application — originally written in Swift — to the Android platform using Kotlin. The original app (https://github.com/DeimanteValunaite/weather-app-swift) provides weather information via a clean mobile UI and serves as the functional baseline for the Android version.

**Validation:** The project is considered successful when the Android application replicates the core features of the Swift original — including weather data display, location handling, and UI layout — and runs correctly on an Android emulator or physical device.

### System and Features
The following components will be developed or adapted:

- **Android app (Kotlin):** A functionally equivalent rewrite of the Swift weather app targeting Android
- **UI layer:** Recreated using Jetpack Compose or XML-based layouts to match the original interface
- **Data layer:** Weather API integration (same or equivalent API endpoint as the Swift version)
- **Location services:** Replaced from CoreLocation (iOS) to Android Location API

### AI Assistance in the Development Process

AI tools are used at multiple stages of the project:

| Stage | AI Tool | Purpose |
|---|---|---|
| Code understanding | Claude / ChatGPT | Analyze and explain the existing Swift codebase |
| Code translation | Claude / GitHub Copilot | Translate Swift logic to equivalent Kotlin code |
| UI migration | Claude / Copilot | Suggest Jetpack Compose equivalents for SwiftUI components |
| API integration | Claude | Generate and adapt network/API calls for Android |
| Testing | Claude / Copilot | Generate unit and UI test cases |
| Documentation | Claude | Write inline documentation and README |

AI acts as a **pair programmer and translator** — it does not replace developer judgment but accelerates understanding of an unfamiliar codebase and platform.

### Architecture / Development Diagram

```
┌─────────────────────────────────────────────────────────┐
│                    SOURCE (iOS / Swift)                  │
│  weather-app-swift (GitHub)                              │
│  SwiftUI Views │ CoreLocation │ URLSession / API calls   │
└────────────────────────┬────────────────────────────────┘
                         │
                         ▼
          ┌──────────────────────────┐
          │     AI-Assisted Layer    │
          │  Claude / GitHub Copilot │
          │  - Code analysis         │
          │  - Swift → Kotlin        │
          │  - SwiftUI → Compose     │
          │  - API adaptation        │
          └──────────────┬───────────┘
                         │
                         ▼
          ┌──────────────────────────┐
          │    Human Review Layer    │
          │  Dorian & Marvin         │
          │  - Validate AI output    │
          │  - Integrate & test      │
          │  - Fix platform issues   │
          └──────────────┬───────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────┐
│                  TARGET (Android / Kotlin)               │
│  Jetpack Compose UI │ Android Location API │ Retrofit    │
└─────────────────────────────────────────────────────────┘
```

---

## Project Plan

| # | Task | Deadline | Owner |
|---|---|---|---|
| 1 | Analyze Swift source code; document structure and features | 15.06.2026 | Both |
| 2 | Set up Android project (Kotlin, Gradle, dependencies) | 15.06.2026 | Marvin |
| 3 | AI-assisted translation of data/network layer (API calls) | 20.06.2026 | Dorian |
| 4 | AI-assisted migration of UI (SwiftUI → Jetpack Compose) | 25.06.2026 | Marvin |
| 5 | Location service integration (CoreLocation → Android API) | 25.06.2026 | Dorian |
| 6 | Integration testing on emulator and physical device | 30.06.2026 | Both |
| 7 | Bug fixing and UI polish | 05.07.2026 | Both |
| 8 | Final documentation and README | 08.07.2026 | Both |

---

## Teamwork and Responsibilities

| Team Member | Responsibilities |
|---|---|
| **Dorian Toth** | Swift source analysis, data/network layer migration, location services, AI prompt engineering |
| **Marvin Aichinger** | Android project setup, UI migration (Jetpack Compose), testing, integration |
| **Both** | Code review, bug fixing, documentation, final presentation |

AI tools are used jointly by both team members throughout the project, with each member responsible for reviewing and validating AI-generated output in their respective areas.
