#!/bin/bash

# === 0. User Input ===
echo "Enter your new project name (e.g., bunApp):"
read projectName

echo "Enter new package name (e.g., com.jba.bun):"
read newPackage

# === 1. Define Paths ===
GIT_REPO="https://github.com/JaberAhamed/BaseApp.git"
DEST_DIR="./$projectName"

OLD_PACKAGE="com.sj.baseapp"
OLD_PACKAGE_PATH="com/sj/baseapp"
NEW_PACKAGE_PATH=$(echo "$newPackage" | sed 's/\./\//g')

# === 2. Clone Template ===
echo "📥 Cloning BaseApp template from GitHub..."
git clone "$GIT_REPO" "$DEST_DIR"
cd "$DEST_DIR" || exit

# === 3. Replace Package Name and Project Name in Files ===
echo "🔄 Updating package names and project name..."
find . -type f \( -name "*.kt" -o -name "*.xml" -o -name "*.kts" \) -exec sed -i '' "s/$OLD_PACKAGE/$newPackage/g" {} +
sed -i '' "s/rootProject.name *= *\".*\"/rootProject.name = \"$projectName\"/" settings.gradle.kts

# === 4. Move Kotlin Files from Old to New Package (main, test, androidTest) ===
for dir in "main" "test" "androidTest"; do
  baseDir="app/src/$dir/java"
  oldPath="$baseDir/$OLD_PACKAGE_PATH"
  newPath="$baseDir/$NEW_PACKAGE_PATH"

  if [ -d "$oldPath" ]; then
    mkdir -p "$newPath"
    mv "$oldPath"/* "$newPath"
  fi
done

# === 5. Clean Up Old Package Folders ===
for dir in "main" "test" "androidTest"; do
  baseDir="app/src/$dir/java"
  rm -rf "$baseDir/com/sj"
  [ -d "$baseDir/com" ] && find "$baseDir/com" -type d -empty -delete
done

# === 6. Update AndroidManifest.xml ===
manifestFile="app/src/main/AndroidManifest.xml"
sed -i '' "s/android:label=\"[^\"]*\"/android:label=\"$projectName\"/" "$manifestFile"
sed -i '' "s/package=\"[^\"]*\"/package=\"$newPackage\"/" "$manifestFile"

# === 7. Update applicationId in app/build.gradle.kts ===
gradleFile="app/build.gradle.kts"
sed -i '' "s/applicationId *= *\"[^\"]*\"/applicationId = \"$newPackage\"/" "$gradleFile"

# === 8. Done ===
echo ""
echo "✅ Project '$projectName' created at: $DEST_DIR"
echo "📦 Package renamed to: $newPackage"
echo "🆔 applicationId updated"
echo "🧹 Old package folders removed"
