# PULSE DRIFT — WARZONE · iOS

The iOS app uses Swift + WKWebView, displaying the HTML5/Canvas game from bundled local files.
The current game source is copied from the existing Android/web game and loads offline.

## Build verification
- The unsigned iOS Xcode build has passed GitHub Actions, including Release configuration.
- The offline HTML game bundle and application bundle identifier have been verified.
- The full AppIcon set (1024, 180, 167, 152, 120, 87, 80, 76, 58, 40, 29px) is now present.
- These icons were generated from the original vector icon.svg using scripts/generate_ios_icons.py,
  avoiding upscaling of the old 512px raster while preserving the same visual design.
- The GitHub simulator smoke test checks installation, launch and screenshot capture. Its result
  should be considered separately from a successful compile.

## Ads
The JavaScript bridge exposes the same API as Android for game compatibility, but the
iOS AdsService is currently a stub: there is **no** Google Mobile Ads SDK or UMP
implementation yet and no production ad delivery. Complete consent, ATT decisions
where applicable, truthful App Privacy and native Ads SDK before app release.

## Pending before TestFlight
- Confirm successful simulator launch (not only simulator compilation).
- Confirm the iOS app icon renders correctly in Xcode/Simulator.
- Set up Apple Developer Program, App Store Connect app record, signing/provisioning.
- Test with a real iPhone via TestFlight.
- Confirm iOS privacy declarations and decide how to handle monetization in the first beta.
- Validate UI safe areas, audio and any privacy-policy/website navigation.
