# 🚀 Android Project Generator

A lightweight shell script that bootstraps a new Android project from a **BaseApp template**.  
It saves time by cloning the template, renaming the project, updating package names, and cleaning up old references — so you can start coding immediately.  

---

## 📖 Overview

Creating new Android projects often involves repetitive steps like copying a base project, updating the package name, changing the project name, and editing Gradle files.  

This tool automates those steps:  

- 📥 Clones the [BaseApp template](https://github.com/JaberAhamed/BaseApp)  
- 🔄 Replaces the **package name** everywhere  
- 📝 Updates **project name**, `settings.gradle.kts`, and `AndroidManifest.xml`  
- 🆔 Updates **applicationId** in `build.gradle.kts`  
- 🧹 Cleans up old package folders  

---

## ⚡ Usage

### 1. Quick Run (no installation required)

Run the script directly from GitHub:

```bash
bash <(curl -s https://raw.githubusercontent.com/JaberAhamed/android-project-generator/main/projecreace.sh)

## License

```
Copyright 2021 JABER BIN AHAMED

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
