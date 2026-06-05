# CLAUDE.md — Project Instructions

## Project Overview

| Field | Value |
|---|---|
| **Project** | Cross-Platform Mobile Migration: Porting a Swift Weather App to Android |
| **Course** | KI-assisted Software Engineering (KIS) |
| **Group** | BB-G1 |
| **Team** | Dorian Toth, Marvin Aichinger |
| **Source App** | https://github.com/DeimanteValunaite/weather-app-swift |
| **Proposal Deadline** | 10.06.2026 |
| **Final Deadline** | 08.07.2026 |

The goal is to port an existing Swift/iOS weather app to Android using Kotlin and Jetpack Compose, with AI tools assisting at every stage of the migration.

---

## Mandatory Behaviors

1. **Log every request.** After every user request, append an entry to `docs/interactions.log` in this exact format:
   ```
   [YYYY-MM-DD] <Short English description of what was asked>
   ```
2. **Never commit** code to git unless the user explicitly asks for it.
3. **No unnecessary code comments.** Only add a comment when the WHY is non-obvious. Never describe what the code does — the code does that itself.
4. **Never overwrite `docs/proposal.md`** unless the user explicitly asks to change it.
5. **Never modify `docs/interactions.log`** by removing or editing existing entries — only append.

---

## Development Guidelines

### Target Stack
- **Language:** Kotlin
- **UI:** Jetpack Compose (no XML layouts)
- **Minimum SDK:** Android 8.0 (API 26)
- **Network:** Retrofit + OkHttp
- **Location:** FusedLocationProviderClient (Google Play Services)
- **Async:** Kotlin Coroutines + Flow
- **Architecture:** MVVM (ViewModel + Repository pattern)
- **Testing:** JUnit4 + Espresso

### Code Style
- Follow standard Kotlin conventions (camelCase functions, PascalCase classes)
- Keep Composables small and single-purpose
- ViewModels hold state; Composables only render it
- Repository classes handle all data/network access

---

## Swift → Kotlin Translation Reference

When translating Swift code to Kotlin, apply these equivalences automatically:

| Swift / iOS | Kotlin / Android |
|---|---|
| `struct` | `data class` |
| `class` (value semantics) | `data class` |
| `enum` with associated values | `sealed class` |
| `protocol` | `interface` |
| `@State` | `mutableStateOf()` in Compose |
| `@Binding` | pass `MutableState` as parameter |
| `@ObservedObject` / `ObservableObject` | `ViewModel` + `StateFlow` |
| `SwiftUI View` (body) | `@Composable fun` |
| `URLSession` | `Retrofit` + `OkHttp` |
| `Codable` / `Decodable` | `@SerializedName` + Gson / Moshi |
| `CoreLocation` | `FusedLocationProviderClient` |
| `CLLocationManager` | `LocationRequest` + permission handling |
| `DispatchQueue.main.async` | `withContext(Dispatchers.Main)` |
| `async/await` (Swift) | `suspend fun` + Coroutines |
| `Optional` (`T?`) | Kotlin nullable (`T?`) — same syntax |
| `guard let` | `?: return` or `?.let { }` |
| `UserDefaults` | `SharedPreferences` or `DataStore` |

---

## AI Workflow Instructions

- **Pattern divergence:** When the Android pattern differs significantly from the iOS approach, briefly explain why (platform constraint, different lifecycle model, etc.).
- **No clean equivalent:** If a Swift feature has no direct Android equivalent, flag it explicitly and propose the best alternative.
- **API calls:** When generating network code, use Retrofit interfaces with `suspend fun` — not callback-based approaches.
- **Permissions:** Always include both manifest declarations and runtime permission handling for location and network features.

---

## File Map

| File | Purpose |
|---|---|
| `CLAUDE.md` | This file — persistent Claude instructions |
| `README.md` | Project README — to be written at end of project |
| `docs/proposal.md` | Project proposal — do not overwrite |
| `docs/interactions.log` | Append-only log of all user requests (English) |
| `ios/` | Original Swift source app (read-only reference) |
| `android/` | Android port (to be created) |
