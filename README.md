# outbox-poc

## Icerik

- [Outbox Design Pattern Nedir?](#outbox-design-pattern-nedir)
- [Diagram](#diagram)
- [Outbox Pattern'in Calisma Mantigi Nasil Isler](#outbox-patternin-calisma-mantigi-nasil-isler)
- [Outbox Pattern Hangi Durumlarda Kullanilir?](#outbox-pattern-hangi-durumlarda-kullanilir)
- [Outbox Pattern Zorluklari ve Cozumleri](#outbox-pattern-zorluklari-ve-cozumleri)
- [Ornek Senaryo](#ornek-senaryo)
- [Debezium nedir?](#debezium-nedir)
- [Debezium'in Bazi Temel Ozellikleri Sunlardir:](#debeziumin-bazi-temel-ozellikleri-sunlardir)

## Outbox Design Pattern Nedir?

Outbox Design Pattern, dagitilmis sistemlerdeki veri tutarliligini ve guvenilirligi artirmak amaciyla kullanilan bir
tasarim desenidir. Bu desen, bir servisin yaptigi degisiklikleri (ornegin, bir veritabani guncellemesi) asenkron bir
sekilde diger servislere iletmek icin kullanilir. Temel mantigi, islem gerceklestiginde, ilgili bilgiler bir "outbox"
kaydinda saklanir ve daha sonra bu kayittaki bilgiler, bir mesaj kuyrugu veya olay akisi araciligiyla diger servislere
iletilir.
Bu sekilde, veri degisiklikleri guvenilir bir sekilde diger hizmetlere aktarilir ve veri tutarliligi saglanir. Outbox
tasarim deseni,
servislerin bagimsiz calismasini ve sistemlerin daha yuksek guvenilirlik seviyelerine ulasmasini destekler.

## Diagram

<p align="center">
    <img src="png/diagram.png" alt="diagram" width="%100" height="%100" style="border-radius: 20px">
</p>

## Outbox Pattern'in Calisma Mantigi Nasil Isler

- Bir veritabani islemi gerceklesir (ornegin, bir kayit eklenir veya guncellenir).
- Bu islem sonucunda bir olay (event) olusturulur veya temsil eden veriler hazirlanir.
- Olusturulan bu olay, ozel bir "outbox" tablosuna kaydedilir.
- Daha sonra bir baska surec veya sistem, outbox tablosundaki olaylari duzenli araliklarla kontrol eder.(Debezium)
- Outbox tablosundaki olaylar, asenkron bir sekilde bir olay akisina (event stream) gonderilir (ornegin, Kafka veya
  RabbitMQ gibi bir mesaj kuyrugu kullanilarak).
- Olay akisini dinleyen sistemler, bu olaylari alir ve isler.

## Outbox Pattern Hangi Durumlarda Kullanilir?

`1. Asenkron Islemler:` Ozellikle asenkron mesajlasma gerektiren uygulamalarda, Outbox pattern veritabani islemlerinin
sonuclarini asenkron olarak baska sistemlere veya servislere iletmek icin kullanilabilir. Bu, uygulamalar arasinda daha
iyi isbirligi ve performans saglar.
</br>
`2. Mikroservis Mimarileri:` Mikroservis mimarileri, bircok bagimsiz hizmetin bir araya geldigi karmasik uygulamalari
destekler. Outbox pattern, bu hizmetler arasinda veri butunlugunu ve tutarliligini korumak icin kullanilabilir.
</br>
`3. Event Sourcing:` Event Sourcing, uygulama durumunu olaylar (events) uzerinden takip eden bir tasarim yaklasimidir.
Outbox pattern, bu olaylarin baska sistemlere iletilmesi icin kullanilabilir.
</br>
`4. Guvenilir Iletisim:` Veritabani islemleri ve olaylarin guvenilir bir sekilde iletilmesi gereken durumlarda, Outbox
pattern kullanilabilir. Eger bir islem basarili bir sekilde tamamlandiysa, ancak olay gonderme islemi basarisiz olursa,
olay tekrar gonderilebilir veya geri alinabilir.
</br>
`5. Veri Tutarsizligi Onleme:` Outbox pattern, veritabani islemleri ve olay gonderme islemleri arasinda bagimsizligi
saglayarak, veri tutarsizligini onlemeye yardimci olur.
</br>
`6. Sistem Genislemesi:` Uygulamanizin buyumesi ve yeni bilesenlerin eklenmesi durumunda, Outbox pattern yeni
bilesenlerin veri akisini kolayca entegre etmenize yardimci olabilir.
</br>


<details>
<summary>Event Sourcing Ne Demek?</summary>
Event Sourcing, bir yazilim uygulamasinin durumunu ve verisini olaylar (events) uzerinden takip etmek ve bu olaylari bir 
kayit mekanizmasiyla saklamak icin kullanilan bir yazilim tasarim yaklasimidir. Geleneksel veritabani yontemlerinden farkli olarak,
Event Sourcing, tum uygulama durumu degisikliklerini olaylar olarak kaydeder ve bu olaylari kronolojik bir sira ile saklar.
</details>

## Outbox Pattern Zorluklari ve Cozumleri:

Outbox pattern, bircok avantaji olsa da bazi zorluklari da beraberinde getirebilir. Iste Outbox pattern kullanirken
karsilasilabilecek zorluklar ve bu zorluklari asmak icin onerilen cozumler:

`1. Islem Sirasi Sorunlari:` Veritabani islemi ve olayin gonderilmesi arasinda islem sirasi sorunlari olabilir.
Ornegin, olay gonderilmeden once veritabani islemi basarisiz olabilir. Bu durumda, islem geri alindiginda olayin
gonderilmesi gerekebilir.

**Cozum:** Islem sirasi sorunlarini ele almak icin "compensating transactions" veya "idempotent operations" gibi
teknikler kullanilabilir. Ayrica, islem sirasini duzenlemek ve asenkron islemleri koordine etmek icin bir is akis
yonetimi sistemi (workflow engine) kullanilabilir.

`2. Atilmis Olaylar:` Olaylarin gonderimi sirasinda hata olusursa, bazi olaylar atilabilir veya kaybedilebilir.

**Cozum:** Atilan olaylari yeniden gondermek icin bir yeniden deneme (retry) mekanizmasi kullanilabilir. Ayni olayin
birkac kez gonderilmesi problem yaratmaz cunku olaylarin idempotent olmasini saglayabilirsiniz.

`3. Performans Sorunlari:` Outbox pattern, her veritabani islemi icin bir olay gonderme islemi olusturdugundan, yuksek
veri islem hacimlerinde performans sorunlarina yol acabilir.

**Cozum:** Performansi artirmak icin islem sirasini ve olay gonderme sureclerini iyilestirmek gerekebilir. Ornegin,
olaylari toplu olarak gondermek veya asenkron islemleri daha iyi olceklendirmek icin teknikler kullanabilirsiniz.

`4. Olceklenebilirlik Zorluklari:` Outbox pattern'i kullanirken, artan is yukleri ve hizli buyume durumlarinda
olceklenebilirlik sorunlari ortaya cikabilir.

**Cozum:** Olceklenebilirlik sorunlarini ele almak icin bulut tabanli hizmetler veya kumeler (clusters)
kullanabilirsiniz. Ayrica, yuk dengeleme ve otomatik olceklendirme gibi teknikleri uygulayarak sistemlerinizi
olceklendirebilirsiniz.

`5. Dis Sistemle Entegrasyon Zorluklari:` Olaylari dis sistemlere iletmek ve dis sistemlerden cevap almak bazen
karmasik olabilir.

**Cozum:** Dis sistemle entegrasyonu basitlestirmek icin API tasarimi ve iletisim protokolleri uzerinde dikkatli
calismak onemlidir. Ayrica, dis sistemlerden gelebilecek hatalara karsi guclu hata yonetimi ve geri alma stratejileri
olusturmalisiniz.

## Ornek Senaryo:

E-ticaret stok yonetimi icin Outbox pattern kullanimi su sekilde orneklenir:

Bir e-ticaret platformunda, urun stoklari guncellendiginde (ornegin, yeni bir urun geldiginde veya bir urunun stok
miktari arttiginda), bu guncellemelerin tum sistemler ve musterilere guvenilir bir sekilde iletilmesi gerekebilir. Stok
guncellemeleri onemlidir cunku musterilerin urunlerin mevcutlugunu ve teslimat suresini bilmesi gerekir.

Outbox pattern kullanarak bu senaryoyu ele alabilirsiniz:

1. Bir stok guncellemesi gerceklestiginde (ornegin, yeni bir urun alindiginda veya mevcut bir urunun stok miktari
   arttiginda), bu guncellemeyi bir "stok guncelleme" olayi olarak belirleyebilirsiniz.

2. Bu stok guncelleme olayini bir "outbox" tablosuna ekleyin. Outbox tablosu, stok guncellemelerini temsil eden tum
   olaylari icerir.

3. Bir Outbox Consumer adi verilen bir sistem, duzenli araliklarla outbox tablosunu kontrol eder ve yeni stok guncelleme
   olaylarini alir.

4. Bu alinan olaylari, ornegin Kafka gibi bir olay akisi sistemine gondererek asenkron bir sekilde diger sistemlere
   iletebilirsiniz.

5. Diger sistemler, bu olaylari alir ve stok guncellemelerini guncellemek veya musterilere bildirmek icin kullanabilir.

Bu yaklasim, e-ticaret platformunun stok guncellemelerini musterilere ve diger sistemlere guvenilir bir sekilde
iletebilmesini saglar. Ayrica, veritabani islemleri ve olay gonderme islemleri arasinda bagimsizlik saglar ve veri
butunlugunu korur.

## Debezium nedir?

Debezium, bircok veritabani sistemini izlemek ve bu veritabanlarindaki degisiklikleri gercek zamanli olarak yakalamak
icin kullanilan acik kaynakli bir CDC (Change Data Capture - Veri Degisikliklerini Yakalama) platformudur. Debezium,
veritabanlarindaki degisiklikleri bir dizi olay (event) olarak yakalar ve bu olaylari bir olay akisi (event stream)
olarak cesitli hedeflere iletebilir. Bu, veritabanlarindaki degisiklikleri izlemek ve bu degisiklikleri diger sistemlere
entegre etmek icin oldukca guclu bir arac saglar.

## Debezium'in Bazi Temel Ozellikleri Sunlardir:

- Gercek Zamanli Izleme
- Degisiklikleri Isleme ve Donusturme
- Entegrasyon Kolayligi
- Veritabani Bagimsizligi

<p align="center">
    <img src="png/debezium-db.png" alt="debezium-db" width="%100" height="%100" style="border-radius: 20px">
</p>

<p align="center">
    <img src="png/debezium-db-2.png" alt="debezium-db" width="%100" height="%100" style="border-radius: 20px">
</p>


- [Docker Uzerinde Calistirma](#docker-uzerinde-calistirma)
- [Tech Stack](#tech-stack)
- [Requirements](#requirements)
- [Build & Run](#build--run)
- [Kaynakca](#kaynakca)