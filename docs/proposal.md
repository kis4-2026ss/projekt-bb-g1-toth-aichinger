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
- **UI layer:** Recreating layouts to match the original interface
- **Data layer:** Weather API integration
- **Location services:** Using an available Location API

### AI Assistance in the Development Process

We will use Claude / Copilot / ChatGPT to:
- understand and explain existing Codebase
- migrate to the new Platform
- translate the Code
- migrate the UI
- integration of APIs

### Development Diagram

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
          │  - UI migration          │
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
          └──────────────────────────┘
```

---

## Project Plan

| # | Task | Deadline | Owner |
|---|---|---|---|
| 1 | Analyze Swift source code; document structure and features | 15.06.2026 | Both |
| 2 | Set up Android project | 15.06.2026 | Marvin |
| 3 | AI-assisted translation of data/network layer (API calls) | 20.06.2026 | Dorian |
| 4 | AI-assisted migration of UI (SwiftUI) | 25.06.2026 | Marvin |
| 5 | Location service integration | 25.06.2026 | Dorian |
| 6 | Integration testing on emulator and physical device | 30.06.2026 | Both |
| 7 | Final documentation and README | 08.07.2026 | Both |

---
