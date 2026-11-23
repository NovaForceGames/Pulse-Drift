import UIKit
import WebKit

/// Full-screen WKWebView host for the bundled HTML5 game.
final class ViewController: UIViewController {
    private var webView: WKWebView!
    private let adsBridge = AdsBridge()

    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = UIColor(red: 7/255, green: 11/255, blue: 25/255, alpha: 1)

        let config = WKWebViewConfiguration()
        config.allowsInlineMediaPlayback = true
        config.mediaTypesRequiringUserActionForPlayback = .all
        config.userContentController.add(adsBridge, name: AdsBridge.handlerName)
        let shim = WKUserScript(source: AdsBridge.jsShim, injectionTime: .atDocumentStart, forMainFrameOnly: true)
        config.userContentController.addUserScript(shim)

        webView = WKWebView(frame: .zero, configuration: config)
        webView.backgroundColor = UIColor(red: 7/255, green: 11/255, blue: 25/255, alpha: 1)
        webView.isOpaque = false
        webView.scrollView.bounces = false
        webView.scrollView.isScrollEnabled = false
        webView.scrollView.contentInsetAdjustmentBehavior = .never
        webView.translatesAutoresizingMaskIntoConstraints = false
        adsBridge.attach(webView: webView)
        view.addSubview(webView)
        NSLayoutConstraint.activate([
            webView.topAnchor.constraint(equalTo: view.topAnchor),
            webView.bottomAnchor.constraint(equalTo: view.bottomAnchor),
            webView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            webView.trailingAnchor.constraint(equalTo: view.trailingAnchor)
        ])

        if let url = Bundle.main.url(forResource: "index", withExtension: "html", subdirectory: "Game") {
            webView.loadFileURL(url, allowingReadAccessTo: url.deletingLastPathComponent())
        }
    }

    override var prefersStatusBarHidden: Bool { true }
    override var supportedInterfaceOrientations: UIInterfaceOrientationMask { .portrait }
}
