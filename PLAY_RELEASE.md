# PULSE DRIFT — Google Play yayın dosyası (TASLAK)

**Geliştirici markası:** NovaForce Games
**Oyun adı:** PULSE DRIFT: WARZONE
**Android paket adı:** `games.novaforce.pulsedrift`
**Kategori:** Oyun / Arcade
**Fiyat planı:** Ücretsiz; yayın izni ve AdMob gerçek reklam birimleri alındıktan sonra isteğe bağlı ödüllü reklam.
**Gizlilik URL:** https://novaforcegames.github.io/Pulse-Drift/privacy.html

## Kısa açıklama
Savaş bölgesinde uç, füze ve mayınlardan kaç, rekorunu kır!

## Uzun açıklama
PULSE DRIFT: WARZONE, NovaForce Games tarafından geliştirilen hızlı tempolu bir uçuş ve refleks oyunudur.

Modern savaş uçağınla üç şeritli tehlikeli bir savaş koridorunda ilerle. Otomatik topların sıradan füzeleri vururken, kalkanlı dronları ve dikenli mayınları çevik hamlelerle atlatman gerekir. Alarm veren ağır zırhlı bombalar çok daha dayanıklıdır.

**Özellikler**
- Tek dokunuş ve kaydırma hareketleriyle kolay kontrol
- Füzeler, kalkanlı dronlar, mayınlar ve ağır bombalar
- Otomatik ateş, patlama sesleri, görsel efektler
- Enerji toplayıp PHASE yeteneğiyle tehlikeden kurtulma
- 5 başlangıç canı, bonus canlar ve kişisel rekor
- Android test sürümünde isteğe bağlı, test reklamıyla +1 can

Oynaması ücretsiz. Rekorunu yükselt ve daha uzun süre hayatta kal!

> Bu metin taslaktır. Reklam, yaş ve veri güvenliği açıklamaları gerçek üretim sürümüne göre güncellenecek.

## Store'a çıkmadan yapılacaklar
- [ ] Google Play Console hesabı ve kimlik doğrulaması
- [ ] Google AdMob hesabı / uygulama kimliği / gerçek rewarded reklam birimi
- [ ] Android uygulamasını gerçek cihazda derleyip test et
- [ ] SDK 36 hedefi ve Android 15/16 ekran uyumluluğu testi
- [ ] UMP izin mesajı ve gizlilik tercihlerinin bölgeye göre test edilmesi
- [ ] Yaş/hedef kitle, reklam, içerik derecelendirmesi ve Data safety beyanı
- [ ] Uygulama simgesi (512×512), ekran görüntüleri ve özellik grafiği
- [ ] Play App Signing ve imzalı AAB oluşturma
- [ ] Yeni kişisel hesaplarda gereken kapalı test (12 test kullanıcısı, 14 gün) / üretim erişimi onayı
- [ ] Gerçek reklamlar için AdMob ve ilgili yayın onayı
- [ ] Önce test cihazlarında ve farklı bölgelerde ad ödüllerini doğrula

## Temel test senaryoları
1. Web sürümünde kaybedince **FREE TEST CONTINUE** bir defa ücretsiz can verir.
2. Android sürümünde +1 can **yalnızca AdMob ödül callback'i** gelince verilir.
3. Android test reklamı açılmaz/oyuncu erken kapatırsa can verilmez.
4. İkinci defa devam hakkı verilmez; yeni oyuna başlama ücretsiz kalır.
5. Reklam izleme seçeneği gönüllüdür, oyun devamı için bir satın alma zorunluluğu yoktur.
6. Gizlilik sayfası ve gerekiyorsa izin seçenekleri açılabilir.
7. İnternet yokken Android oyunu oynanır; reklam sunulmadığında oyun kilitlenmez.
8. Skor ve ses tercihleri yerel depolamada doğru kalır.

**Önemli:** Debug APK, Google test reklamları ve bu taslaklar gerçek kazanç sağlamaz; yayın hazır ilan edilmemelidir.
