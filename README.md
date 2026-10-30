# Rima Nasha Ghosh Tic Tac Toe Game

Private, offline, two-player Android game for family use.

## Build the APK in GitHub's cloud (no software installation on your computer)

1. Sign in to GitHub in your browser and create a **new public repository** named `rima-nasha-ghosh-tic-tac-toe`.
   - A public repository allows GitHub Actions to run without requiring a paid GitHub plan.
   - Do not add a README, license, or `.gitignore` during repository creation.
2. Open the new repository and choose **Add file → Upload files**.
3. Extract this ZIP on your computer, then upload the project contents (including the hidden `.github` folder) into the repository. If your browser's file picker hides `.github`, upload the workflow separately by creating `.github/workflows/android-apk.yml` in GitHub and pasting its contents from this ZIP.
4. Commit the files to the `main` branch.
5. Open the repository's **Actions** tab. Select **Build Android APK**, then **Run workflow** if it has not started automatically.
6. Wait for the workflow to finish with a green check.
7. Open the completed workflow run. Under **Artifacts**, download `Rima-Nasha-Ghosh-Tic-Tac-Toe-APK`.
8. Extract the downloaded artifact ZIP. It contains `app-debug.apk`. Transfer that APK to your Android phone and tap it to install. Allow your browser or file manager to install unknown apps when Android asks.

## Build configuration
- Application ID: `com.rimanashaghosh.tictactoe`
- Minimum Android version: Android 6.0 (API 23)
- Target SDK: 35
- Version: 1.0
- Offline; no login, ads, analytics, or internet permission.

The workflow builds a debug APK, signed with Android's standard debug key so it can be installed directly for personal use. This is suitable for private family use, not Play Store distribution.
