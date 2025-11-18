@echo off
echo ========================================
echo Simple APK Builder
echo ========================================

echo Step 1: Checking Java...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo.
    echo ❌ JAVA NOT FOUND
    echo.
    echo Please install Java manually:
    echo 1. Go to: https://adoptium.net/temurin/releases/
    echo 2. Download JDK 17 (Windows MSI)
    echo 3. Install it (2 minutes)
    echo 4. Run this script again
    echo.
    pause
    exit /b 1
)

echo ✓ Java is installed
java -version

echo.
echo Step 2: Generating project...
python generate_android_project_simple.py
cd MyWordsApp

echo.
echo Step 3: Building APK...
echo This will take 10-15 minutes...
gradlew.bat assembleDebug

if %errorlevel% equ 0 (
    echo ✅ SUCCESS! APK ready: app\build\outputs\apk\debug\app-debug.apk
) else (
    echo ❌ Build failed
)

pause