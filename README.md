# 🧪 Jetpack Compose Demo App

This is a learning project built with **Jetpack Compose** that demonstrates modern Android UI development with API integration, navigation, and theming support.

---

## 📱 Features

### 🚀 Bottom Navigation with 3 Tabs:
1. **🛒 Products**
   - Fetches product list from API
   - Detail page
2. **🖼 Gallery**
   - Displays color photo thumbnails via an API call
   - Uses `LazyVerticalGrid` for layout
3. **✅ To-Do**
   - Basic placeholder screen to demonstrate screen structure

---

## 🧱 Tech Stack

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Material 3](https://developer.android.com/jetpack/compose/designsystems/material3)
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
- [Hilt (DI)](https://developer.android.com/training/dependency-injection/hilt-android)
- [Navigation Compose](https://developer.android.com/jetpack/compose/navigation)
- [Coil](https://coil-kt.github.io/coil/) for image loading
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- MVVM architecture

---

## 🎯 Purpose

This project was built by **Tirth Patel** as a hands-on exercise to:
- Learn Jetpack Compose UI toolkit
- Explore modern Android app architecture
- Practice API integration, dynamic theming, and navigation

---

## 📸 Screenshots

__

---

## 📦 Project Structure

```

app/
├── core.ui/
├── ui/
│ ├── activity/ # MainActivity & screen composables
│ ├── navigation/ # Bottom navigation & nav graph
│ ├── theme/ # Material3 light/dark themes
├── repository/ # API service & model classes
├── network/ # API service & model classes
├── util/ # Extensions and utility functions
├── viewmodel/ # ViewModels for each feature


````

---

## 🛠 Setup

1. Clone this repo  
   ```bash
   git clone https://github.com/Tirth-TP/ComposeDemoApp.git
Open in Android Studio (Hedgehog or newer recommended)

Make sure you're using Kotlin 2.0+ and Compose Compiler 1.5.11+

Run the app on an emulator or device

---

## 📄 License
This project is open-source and available under the [MIT License](./LICENSE).

```
