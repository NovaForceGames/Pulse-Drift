# NovaForce Games — PULSE DRIFT gelir planı

## Şu an hazırlananlar
- Web oyununda **oyun sonunda ücretsiz TEST devam (+1 can)**; reklam değildir, gelir getirmez.
- Android projesi `android/` klasöründe, AdMob **yalnızca Google TEST reklam kimlikleri** ile.
- Gizlilik sayfası: `privacy.html`.
- Gerçek reklam ödülü yalnızca reklam SDK'sının `onUserEarnedReward` çağrısıyla verilecek; reklam çıkmadıysa ödül yok.
- İkinci devam hakkı yok; oyuncu istediği zaman ücretsiz tekrar başlatabilir.

## Yayın için hesap sahiplerinin yapması gerekenler
1. [Google Play Console](https://play.google.com/console/signup) geliştirici hesabını aç, doğrulamaları yap; ücret gerekiyorsa kendi adına öde. Hesap ve ödeme bilgilerini repoya koyma.
2. [Google AdMob](https://admob.google.com/) hesabı aç, Android uygulamayı ekle, uygulama kimliği ve ödüllü reklam birimi oluştur. Test kimliklerini sadece yayın öncesi değiştirmek yeterli değildir: uygulamayı test et, onay ve politika gerekliliklerini tamamla.
3. AdMob **Privacy & messaging** bölümünde uygun izin mesajlarını hazırla ve uygulamada gerekli gizlilik tercihi girişini göster.
4. Google Play'de gizlilik politikası URL'si, doğru Data safety formu, reklam beyanı, hedef kitle beyanı, mağaza grafikleri ve uygulama imzalama adımlarını tamamla.
5. Yeni bireysel Play geliştirici hesaplarında, şartlara göre en az **12 kişinin kesintisiz 14 gün kapalı testte** kayıtlı olması gerekebilir.
6. Android projesini Android Studio'da derle, gerçek Android telefonla test et; **herhangi bir test derlemesini halka açık mağazaya yüklemeden** önce gerçek reklam kimliklerini ayarla.

## Web H5 Games Ads
- [AdSense H5 Games Ads başvuru](https://adsense.google.com/start/h5-game-ads-apply/): ayrı başvuru ve onay gerekiyor. GitHub Pages bağlantısına kod eklemek tek başına gelir sağlamaz.
- Onay alınırsa [Google Ad Placement API](https://developers.google.com/ad-placement/docs/overview) resmi yöntemi kullanılacak. Aktif oyun sırasında zorunlu reklam yok.

## Durum
**Yayın ve gerçek gelir henüz başlamadı.** Test reklamları ve ücretsiz test devamı gelir üretmez.
