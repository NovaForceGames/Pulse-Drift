import Foundation

protocol AdsServiceDelegate: AnyObject {
    func adsServiceDidEarnReward()
    func adsServiceDidBecomeUnavailable()
}

/// TEST INFRASTRUCTURE ONLY. No ad SDK is linked; no fake rewards are granted.
final class AdsService {
    weak var delegate: AdsServiceDelegate?

    func showRewardedAd() {
        delegate?.adsServiceDidBecomeUnavailable()
    }

    func openPrivacyChoices() {
        // Implement with Google UMP and the actual iOS Ads SDK before release.
    }
}
