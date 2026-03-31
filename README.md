## 📊 Smart Data Sharing Platform

Bu proje, Spring Boot kullanılarak geliştirilmiş, JWT tabanlı kimlik doğrulama içeren, veri paylaşımı ve versiyonlama mantığına sahip bir RESTful API uygulamasıdır.

Sistem; kullanıcıların veri paylaşabilmesini, verileri güncelleyebilmesini ve yapılan işlemlerin kayıt altına alınmasını (audit log) sağlar.

---

## 🎯 Proje Amacı

- Kullanıcıların veri paylaşabileceği bir sistem geliştirmek  
- Verilerin versiyonlanarak geçmişinin korunmasını sağlamak  
- Yapılan işlemleri kayıt altına almak  
- Güvenli bir API mimarisi oluşturmak  

---

## 🏗️ Uygulama Mimarisi

Proje katmanlı mimari yapıya sahiptir.

### Katmanlar

| Katman | Açıklama |
|--------|---------|
| api | Controller katmanı |
| business | İş kurallarının yazıldığı servis katmanı |
| dataAccess | Veritabanı işlemlerinin yapıldığı repository katmanı |
| entities | JPA entity sınıfları |
| config | Security ve JWT yapılandırmaları |
| exception | Global hata yönetimi |

---

## 🚀 Özellikler

- JWT tabanlı authentication  
- Kullanıcı kayıt ve giriş sistemi  
- Veri ekleme  
- Veri güncelleme (versiyonlama ile)  
- Veri listeleme (pagination destekli)  
- Action log (işlem kayıt sistemi)  
- Spring Security ile güvenlik  
- Swagger API dokümantasyonu  

---

## 🔐 Authentication Süreci

1. Kullanıcı kayıt olur  
2. Giriş yapar  
3. JWT token alır  
4. Token ile korumalı endpointlere erişir  

Authorization header:
Authorization: Bearer <TOKEN>


---

## 📌 API Endpointleri

### 👤 Kullanıcı İşlemleri

| Method | Endpoint | Açıklama |
|--------|----------|---------|
| POST | /api/users/register | Kullanıcı kaydı |
| POST | /api/users/login | Giriş yap |

### 📁 Veri İşlemleri

| Method | Endpoint | Açıklama |
|--------|----------|---------|
| POST | /api/shared-data | Veri ekle |
| GET | /api/shared-data | Verileri listele |
| PUT | /api/shared-data | Veri güncelle |

---

## 🔄 Versiyonlama Mantığı

Bir veri güncellendiğinde:

- Eski veri ARCHIVED durumuna alınır  
- Yeni veri ACTIVE olarak oluşturulur  
- Versiyon numarası artırılır  

Bu sayede veri geçmişi korunur.

---

## 🧾 Action Log Sistemi

Sistemde yapılan işlemler kayıt altına alınır.

Kayıt edilen bilgiler:

- İşlem tipi  
- Açıklama  
- Veri ID  
- İşlemi yapan kullanıcı  
- Zaman bilgisi  

---

## ⚙️ Kurulum

Projeyi klonlayın:

```bash
git clone https://github.com/kullaniciadi/proje-adi.git
cd proje-adi
```

---

## Veritabanı ayarlarını yapın:

```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/db_name
spring.datasource.username=postgres
spring.datasource.password=1234
```

---

## Uygulamayı çalıştırın:

```bash
mvn spring-boot:run
```

---

## 📘 Swagger

Uygulama çalıştıktan sonra:

```bash
http://localhost:8090/swagger-ui/index.html
```

---

##  🧪 Testler

- Mockito kullanılarak unit test yazılmıştır
- Servis katmanı test edilmiştir

---

## 🛡️ Güvenlik

- JWT tabanlı kimlik doğrulama
- BCrypt ile şifreleme
- Stateless yapı

---

## ❗ Hata Yönetimi

Global Exception Handler ile:

- Runtime hataları
- JWT hataları
- Validation hataları

standart formatta döndürülür.

---

### 👩‍💻 Geliştirici

**Fatmagül Yılmaz**
