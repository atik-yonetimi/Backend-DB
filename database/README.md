# Akıllı Atık Yönetimi Veritabanı

Bu klasör, Akıllı Atık Yönetimi projesinin PostgreSQL veritabanı dosyalarını içerir.

Bu veritabanı; konteynerleri, araçları, sürücüleri, rota planlarını, atık toplama işlemlerini, misafir şikayetlerini ve haftalık atık miktarı takibini yönetmek için tasarlanmıştır.

---

## Projenin Amacı

Bu sistemin amacı, farklı atık türlerine ait konteynerlerin takibini yapmak, araçların bu konteynerlerden atık toplamasını kaydetmek ve yöneticinin sistem üzerindeki verileri kontrol edebilmesini sağlamaktır.

### Sistem Akışı

1. Konteynerler kampüs içerisine yerleştirilir  
2. Araçlar atık türlerine göre tanımlanır  
3. Sürücüler plaka ve şifre ile giriş yapar  
4. Araçlar konteynerleri ziyaret eder  
5. Toplama işlemleri kaydedilir  
6. Haftalık toplamlar güncellenir  
7. Yönetici sistemi kontrol eder  

---

## Kullanılan Teknolojiler

- PostgreSQL
- Docker
- SQL
- Git / GitHub

---

## Veritabanı Kurulumu

### Veritabanı oluştur:

```bash
createdb -U postgres atik_yonetimi
```

### SQL yükle:

```bash
psql -U postgres -d atik_yonetimi < database/atik_yonetimi.sql
```

### Docker:

```bash
docker exec -i atik-postgres psql -U postgres -d atik_yonetimi < database/atik_yonetimi.sql
```

---

## Atık Türleri

- CAM
- PLASTIK
- KAGIT
- IKINCI_EL_ESYA
- METAL

---

## Araçlar (vehicles)

- Toplam: 10 araç  
- Her kategoride: 2 araç  

Araç bilgileri:

- Plaka  
- Atık türü  
- Garaj konumu  
- Şifre  

Giriş örneği:

```sql
SELECT id, plate
FROM vehicles
WHERE plate = '01ABC001'
AND login_password = 'plastik02pass';
```

---

## Konteynerler (containers)

- Toplam: 75 konteyner  
- Her kategoride: 15 konteyner  
- 15 farklı konum  

Her konumda:

- 1 CAM  
- 1 PLASTIK  
- 1 KAGIT  
- 1 IKINCI_EL_ESYA  
- 1 METAL  

---

## Telemetry

Konteynerlerden gelen anlık veriler tutulur:

- Doluluk
- Pil seviyesi
- Zaman bilgisi

---

## View: latest_container_state

Her konteynerin en güncel durumunu gösterir.

---

## Sürücüler

- Araçlarla ilişkilidir  
- Foreign key ile bağlıdır  

---

## Rota Sistemi

### route_plans

- Araç bazlı rota

### route_stops

Durumlar:

- PENDING  
- DONE  
- SKIPPED  

---

## Toplama İşlemleri (collections)

- Toplanan kg tutulur  
- GPS bilgisi saklanır  
- Driver ve araç ilişkili  

---

## Idempotency

Aynı işlemin tekrar kaydedilmesini engeller.

---

## Haftalık Atık Takibi

`weekly_waste_totals` tablosu:

- Haftalık kg tutar  
- Otomatik güncellenir (trigger)  

Sıfırlama:

```sql
SELECT reset_weekly_waste_totals();
```

---

## Şikayet Sistemi

Misafirler şikayet gönderir → `complaints` tablosu

Yönetici:

- Görüntüler  
- Siler  

---

## Yönetici İşlemleri

- Araç ekleme  
- Araç silme  
- Şikayet yönetimi  
- Haftalık verileri görme  

---

## Foreign Key Yapısı

Tablolar birbirine bağlıdır:

- drivers → vehicles  
- telemetry → containers  
- collections → vehicles / drivers / route_stops  

---

## Constraint Kuralları

- Latitude / Longitude sınırları  
- Negatif kg yasak  
- DONE → amount zorunlu  
- SKIPPED → reason zorunlu  
- Plate UNIQUE  

---

## Index Kullanımı

Performans için:

- telemetry index  
- route index  
- collections index  

---

## Sistem Özeti

- 5 atık türü  
- 10 araç  
- 75 konteyner  
- 15 lokasyon  
- Login sistemi  
- Şikayet sistemi  
- Haftalık atık takibi  

---

## Not

Bu proje eğitim amaçlıdır.

Gerçek sistemde:

- Şifreler hashlenmelidir  
- Yetkilendirme yapılmalıdır  
- Cron job ile haftalık reset yapılmalıdır  
