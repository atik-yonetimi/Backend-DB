# ♻️ Akıllı Atık Yönetimi Veritabanı

Bu klasör, Akıllı Atık Yönetimi projesinin PostgreSQL veritabanını içerir.

Sistem; konteynerler, araçlar, sürücüler, rota planları, atık toplama işlemleri, şikayetler ve haftalık atık takibini yönetmek için tasarlanmıştır.

---

## 🎯 Projenin Amacı

Bu sistemin amacı:

- Atık konteynerlerini takip etmek  
- Araçlarla toplama işlemlerini yönetmek  
- Haftalık atık analizini yapmak  
- Kullanıcı ve yönetici etkileşimini sağlamak  

---

## ⚙️ Sistem Akışı

```mermaid
flowchart TD
    A[Konteynerler] --> B[Araçlar]
    B --> C[Sürücü Girişi]
    C --> D[Toplama İşlemi]
    D --> E[Collections Tablosu]
    E --> F[Weekly Waste Totals]
    G[Misafir] --> H[Şikayet Gönderir]
    H --> I[Complaints Tablosu]
    I --> J[Yönetici]
```

---

## 🧱 ER Diyagramı

```mermaid
erDiagram
    VEHICLES ||--o{ DRIVERS : assigned_to
    VEHICLES ||--o{ ROUTE_PLANS : used_in
    VEHICLES ||--o{ COLLECTIONS : performs

    CONTAINERS ||--o{ TELEMETRY : sends
    CONTAINERS ||--o{ ROUTE_STOPS : included_in

    ROUTE_PLANS ||--o{ ROUTE_STOPS : contains
    ROUTE_STOPS ||--o{ COLLECTIONS : collected_at

    DRIVERS ||--o{ COLLECTIONS : makes

    VEHICLES {
        bigint id PK
        varchar plate
        waste_type_enum waste_type
    }

    CONTAINERS {
        bigint id PK
        waste_type_enum waste_type
        double lat
        double lng
    }

    COLLECTIONS {
        bigint id PK
        numeric amount_kg
        timestamptz collected_at
    }
```

---

## 🛠️ Kullanılan Teknolojiler

- PostgreSQL  
- Docker  
- SQL  
- Git & GitHub  

---

## 🚀 Kurulum

```bash
createdb -U postgres atik_yonetimi
psql -U postgres -d atik_yonetimi < database/atik_yonetimi.sql
```

Docker:

```bash
docker exec -i atik-postgres psql -U postgres -d atik_yonetimi < database/atik_yonetimi.sql
```

---

## 🗑️ Atık Türleri

- CAM  
- PLASTIK  
- KAGIT  
- IKINCI_EL_ESYA  
- METAL  

---

## 🚛 Araçlar

- Toplam: **10 araç**
- Her kategoride: **2 araç**

Giriş sistemi:

```sql
SELECT id, plate
FROM vehicles
WHERE plate = '01ABC001'
AND login_password = 'plastik02pass';
```

---

## 📍 Konteynerler

- Toplam: **75 konteyner**
- Her kategoride: **15 adet**
- **15 farklı lokasyon**

Her lokasyonda:

✔️ 5 farklı atık türü

---

## 📡 Telemetry

Konteynerlerden gelen:

- Doluluk  
- Pil seviyesi  
- Zaman bilgisi  

---

## 🔄 Toplama Sistemi

Toplama işlemleri `collections` tablosunda tutulur.

✔️ kg bilgisi  
✔️ GPS konumu  
✔️ sürücü & araç ilişkisi  

---

## 🧠 Haftalık Atık Takibi

`weekly_waste_totals` tablosu:

- Haftalık kg hesaplar  
- Otomatik güncellenir  

Reset:

```sql
SELECT reset_weekly_waste_totals();
```

---

## 🧾 Şikayet Sistemi

Misafir → Şikayet gönderir  
→ `complaints` tablosuna kaydedilir  

Yönetici:

- Görür  
- Siler  

---

## 👨‍💼 Yönetici Yetkileri

- Araç ekleme  
- Araç silme  
- Şikayet yönetimi  
- Veri izleme  

---

## 🔗 Veri İlişkileri

- drivers → vehicles  
- telemetry → containers  
- collections → vehicles  

---

## 🛡️ Constraint Kuralları

- Negatif kg yasak  
- Plate UNIQUE  
- DONE → amount zorunlu  
- SKIPPED → reason zorunlu  

---

## ⚡ Sistem Özeti

| Özellik | Değer |
|--------|------|
| Atık Türü | 5 |
| Araç | 10 |
| Konteyner | 75 |
| Lokasyon | 15 |

---

## ⚠️ Not

Bu proje eğitim amaçlıdır.

Gerçek sistemde:

- Şifreler hashlenmelidir  
- Yetkilendirme eklenmelidir  
- Cron job ile otomasyon yapılmalıdır  

---
