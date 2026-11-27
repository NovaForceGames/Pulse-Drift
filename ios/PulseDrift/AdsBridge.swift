import WebKit

/// Separate iOS ads bridge, sharing only the JavaScript call surface.
final class AdsBridge: NSObject, WKScriptMessageHandler {
    static let handlerName = "NovaForceIOSAds"

    static let jsShim = """
    window.NovaForceAndroidAds = {
      showRewardedAd: function() { window.webkit.messageHandlers.\(handlerName).postMessage({action: 'showRewardedAd'}); },
      openPrivacyChoices: function() { window.webkit.messageHandlers.\(handlerName).postMessage({action: 'openPrivacyChoices'}); }
    };
    """

    private weak var webView: WKWebView?
    private let adsService = AdsService()

    func attach(webView: WKWebView) {
        self.webView = webView
        adsService.delegate = self
    }

    func userContentController(_ userContentController: WKUserContentController,
                               didReceive message: WKScriptMessage) {
        guard message.name == AdsBridge.handlerName,
              let body = message.body as? [String: Any],
              let action = body["action"] as? String else { return }
        switch action {
        case "showRewardedAd":
            adsService.showRewardedAd()
        case "openPrivacyChoices":
            adsService.openPrivacyChoices()
        default:
            break
        }
    }

    fileprivate func notifyGame(_ event: String) {
        let js = "window.NovaForceGame && window.NovaForceGame.\(event)()"
        DispatchQueue.main.async { [weak self] in
            self?.webView?.evaluateJavaScript(js, completionHandler: nil)
        }
    }
}

extension AdsBridge: AdsServiceDelegate {
    func adsServiceDidEarnReward() { notifyGame("onReward") }
    func adsServiceDidBecomeUnavailable() { notifyGame("onAdUnavailable") }
}
