package games.novaforce.pulsedrift;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.widget.Toast;

import androidx.webkit.WebViewAssetLoader;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.UserMessagingPlatform;

public final class MainActivity extends Activity {
    private static final String LOCAL_HOST = "appassets.androidplatform.net";
    private WebView webView;
    private RewardedAd rewardedAd;
    private ConsentInformation consentInformation;
    private boolean adsInitialized = false;
    private boolean adLoading = false;

    @Override
    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.rgb(7, 11, 25));
        getWindow().setNavigationBarColor(Color.rgb(7, 11, 25));

        final WebViewAssetLoader assetLoader = new WebViewAssetLoader.Builder()
                .addPathHandler("/assets/", new WebViewAssetLoader.AssetsPathHandler(this))
                .build();
        webView = new WebView(this);
        webView.setBackgroundColor(Color.rgb(7, 11, 25));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowFileAccess(false);
        webView.getSettings().setAllowContentAccess(false);
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(true);
        webView.setWebChromeClient(new WebChromeClient());
        // Local, bundled appassets origin; all other navigation opens in the system browser.
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return assetLoader.shouldInterceptRequest(request.getUrl());
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri uri = request.getUrl();
                if ("https".equals(uri.getScheme()) && LOCAL_HOST.equals(uri.getHost())) return false;
                if ("https".equals(uri.getScheme()) || "http".equals(uri.getScheme())) {
                    try { startActivity(new Intent(Intent.ACTION_VIEW, uri)); }
                    catch (Exception ignored) { }
                }
                return true;
            }
        });
        webView.addJavascriptInterface(new GameAdsBridge(), "NovaForceAndroidAds");
        setContentView(webView);
        webView.loadUrl("https://" + LOCAL_HOST + "/assets/index.html");
        requestConsentOnLaunch();
    }

    private void requestConsentOnLaunch() {
        consentInformation = UserMessagingPlatform.getConsentInformation(this);
        ConsentRequestParameters params = new ConsentRequestParameters.Builder().build();
        consentInformation.requestConsentInfoUpdate(
                this, params,
                () -> UserMessagingPlatform.loadAndShowConsentFormIfRequired(
                        MainActivity.this, formError -> initializeAdsIfPermitted()),
                requestError -> initializeAdsIfPermitted()
        );
    }

    private void initializeAdsIfPermitted() {
        if (consentInformation == null || !consentInformation.canRequestAds()) {
            rewardedAd = null;
            return;
        }
        if (adsInitialized) {
            loadRewardedAd();
            return;
        }
        adsInitialized = true;
        new Thread(() -> MobileAds.initialize(MainActivity.this,
                status -> runOnUiThread(this::loadRewardedAd))).start();
    }

    private void loadRewardedAd() {
        if (consentInformation == null || !consentInformation.canRequestAds()
                || rewardedAd != null || adLoading) return;
        adLoading = true;
        RewardedAd.load(
                this, BuildConfig.REWARDED_AD_UNIT_ID, new AdRequest.Builder().build(),
                new RewardedAdLoadCallback() {
                    @Override public void onAdLoaded(RewardedAd ad) {
                        adLoading = false;
                        rewardedAd = ad;
                    }
                    @Override public void onAdFailedToLoad(LoadAdError error) {
                        adLoading = false;
                        rewardedAd = null;
                    }
                });
    }

    private void showRewardedAd() {
        if (consentInformation == null || !consentInformation.canRequestAds() || rewardedAd == null) {
            notifyGame("onAdUnavailable");
            loadRewardedAd();
            return;
        }
        final RewardedAd ad = rewardedAd;
        rewardedAd = null;
        ad.setFullScreenContentCallback(new FullScreenContentCallback() {
            private boolean awarded = false;

            @Override public void onAdDismissedFullScreenContent() {
                if (!awarded) notifyGame("onAdUnavailable");
                loadRewardedAd();
            }
            @Override public void onAdFailedToShowFullScreenContent(AdError error) {
                notifyGame("onAdUnavailable");
                loadRewardedAd();
            }

            // Called only after AdMob confirms that the reward criteria were met.
            void recordReward() { awarded = true; }
        });
        // The game receives +1 life ONLY from the SDK reward callback.
        ad.show(this, rewardItem -> notifyGame("onReward"));
    }

    private void notifyGame(String event) {
        // 'event' comes exclusively from hard-coded internal method calls.
        runOnUiThread(() -> {
            if (webView != null) webView.evaluateJavascript(
                    "window.NovaForceGame && window.NovaForceGame." + event + "()", null);
        });
    }

    private final class GameAdsBridge {
        @JavascriptInterface public void showRewardedAd() {
            runOnUiThread(MainActivity.this::showRewardedAd);
        }
        @JavascriptInterface public void openPrivacyChoices() {
            runOnUiThread(() -> {
                if (consentInformation != null && consentInformation.getPrivacyOptionsRequirementStatus()
                        == ConsentInformation.PrivacyOptionsRequirementStatus.REQUIRED) {
                    UserMessagingPlatform.showPrivacyOptionsForm(MainActivity.this,
                            error -> initializeAdsIfPermitted());
                } else {
                    Toast.makeText(MainActivity.this, "No additional privacy choices required.",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override public void onBackPressed() {
        if (webView != null && webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }
    @Override protected void onPause() {
        if (webView != null) webView.onPause();
        super.onPause();
    }
    @Override protected void onResume() {
        super.onResume();
        if (webView != null) webView.onResume();
    }
    @Override protected void onDestroy() {
        if (webView != null) {
            webView.removeJavascriptInterface("NovaForceAndroidAds");
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
