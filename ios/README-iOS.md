# PULSE DRIFT — WARZONE · iOS

Swift + WKWebView shell; the game runs from bundled local HTML5/Canvas files.
Game source files are byte-identical to `index.html`, `privacy.html`, and `icon.svg` on the main branch.

## Ads
The iOS bridge supplies a JavaScript compatibility shim for `NovaForceAndroidAds`,
but `AdsService` is intentionally a **test stub** with no Google Mobile Ads SDK.
It reports `onAdUnavailable` rather than granting a fake reward. Add Mobile Ads SDK,
UMP consent, and ATT checks in a separate reviewed change.

## Build
Run the GitHub Actions "iOS build check" workflow on the `ios-preparation` branch,
or build with Xcode. No Apple credentials are required for the unsigned build.

## Pending before TestFlight
- Restore the full app icon assets from Muse's reviewed ZIP (temporary empty catalog here).
- Validate iPhone landscape/safe-area behavior and audio.
- Connect AdMob iOS SDK and accurate privacy disclosures.
- Enroll in Apple Developer Program and securely provision code signing.
