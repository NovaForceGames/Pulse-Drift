# NovaForce Games — PULSE DRIFT Android test app

**Status:** Developer project, NOT a published Google Play application.

- Same bundled `../index.html` game as the public web edition (playable offline).
- Android WebView + safe local asset origin; external links use the device browser.
- AdMob rewarded ads: Google's **demo App ID / demo rewarded ad unit ID** by default.
- Google UMP privacy consent flow runs before ad requests. A Privacy choices entry exists on the game's main menu.
- +1 life only on `onUserEarnedReward` callback; ad unavailable/cancelled = no reward.
- Google Play 2026 target SDK: API 36.
- Source: Google Mobile Ads SDK 25.5.0 and UMP 4.0.0.

## Build test APK

Open `android/` in Android Studio with Android SDK 36 and JDK 17; sync and run.

Or visit GitHub **Actions → Android test APK → latest build → Artifacts** to download the APK once the workflow succeeds. The debug APK is for testing only.

## Before public release

1. Open your own [Google Play Console](https://play.google.com/console) and [AdMob](https://admob.google.com/) accounts.
2. Register the Android package `games.novaforce.pulsedrift` in AdMob and create a rewarded ad unit. Replace sample IDs only after private tests, via private build Gradle properties: `-PNOVAFORCE_ADMOB_APP_ID=...` and `-PNOVAFORCE_REWARDED_UNIT_ID=...`.
3. Configure AdMob **Privacy & messaging** for the intended audience/regions and check the Google Play Data safety disclosure against the actual SDK data practices.
4. Set up Android App Signing / upload signing key and build a signed AAB (`bundleRelease`) for the Play Console.
5. Complete Play Console account verification, policy declarations, listing screenshots, closed testing, and Google production review.

Do not submit the debug APK or Google's demo ad IDs as a monetized public release. Never commit signing keys, private IDs, or user account credentials.
