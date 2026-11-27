import UIKit
import WebKit

/// Full-screen WKWebView host for the bundled HTML5 game.
final class ViewController: UIViewController, WKNavigationDelegate {
    private var gameDirectory: URL?
    private var webView: WKWebView!
    private let adsBridge = AdsBridge()

    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = UIColor(red: 7/255, green: 11/255, blue: 25/255, alpha: 1)

        let config = WKWebViewConfiguration()
        config.allowsInlineMediaPlayback = true
        config.mediaTypesRequiringUserActionForPlayback = .all
        // No real iOS rewarded-ad SDK is bundled yet. Do not inject the Android-style
        // JavaScript shim: the game then correctly shows its labeled FREE TEST CONTINUE.
        // Re-enable the bridge only when AdsService is backed by actual rewarded ads.

        webView = WKWebView(frame: .zero, configuration: config)
        webView.navigationDelegate = self
        webView.backgroundColor = UIColor(red: 7/255, green: 11/255, blue: 25/255, alpha: 1)
        webView.isOpaque = false
        webView.scrollView.bounces = false
        webView.scrollView.isScrollEnabled = false
        webView.scrollView.contentInsetAdjustmentBehavior = .never
        webView.translatesAutoresizingMaskIntoConstraints = false
        // Intentionally omit adsBridge.attach until the native ads SDK is integrated.
        view.addSubview(webView)
        NSLayoutConstraint.activate([
            webView.topAnchor.constraint(equalTo: view.topAnchor),
            webView.bottomAnchor.constraint(equalTo: view.bottomAnchor),
            webView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            webView.trailingAnchor.constraint(equalTo: view.trailingAnchor)
        ])

        if let url = Bundle.main.url(forResource: "index", withExtension: "html", subdirectory: "Game") {
            gameDirectory = url.deletingLastPathComponent().standardizedFileURL.resolvingSymlinksInPath()
            webView.loadFileURL(url, allowingReadAccessTo: url.deletingLastPathComponent())
        }
    }

    // Keep navigation inside the bundled game, including its local privacy page.
    private func isAllowedGameURL(_ url: URL?) -> Bool {
        guard let url = url, url.isFileURL, let directory = gameDirectory else { return false }
        let resolved = url.standardizedFileURL.resolvingSymlinksInPath()
        return resolved.path.hasPrefix(directory.path + "/")
    }

    func webView(_ webView: WKWebView, decidePolicyFor navigationAction: WKNavigationAction,
                 decisionHandler: @escaping (WKNavigationActionPolicy) -> Void) {
        decisionHandler(navigationAction.targetFrame != nil && isAllowedGameURL(navigationAction.request.url) ? .allow : .cancel)
    }

    func webView(_ webView: WKWebView, decidePolicyFor navigationResponse: WKNavigationResponse,
                 decisionHandler: @escaping (WKNavigationResponsePolicy) -> Void) {
        decisionHandler(isAllowedGameURL(navigationResponse.response.url) ? .allow : .cancel)
    }

    override var prefersStatusBarHidden: Bool { true }
    override var supportedInterfaceOrientations: UIInterfaceOrientationMask { .portrait }
}
