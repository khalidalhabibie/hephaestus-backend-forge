# Pretest - Spring Boot REST API

Jawab pertanyaan berikut dengan bahasa sendiri sebelum membaca materi Spring Boot REST API.

### 1. Apa itu Spring Boot?

Jawaban:

```text
spring Boot adalah framework Java yang digunakan untuk membuat aplikasi backend (terutama REST API) dengan cepat karena sudah menyediakan konfigurasi otomatis dan server bawaan
```

### 2. Apa itu REST API?

Jawaban:

```text
REST API adalah cara komunikasi antara client dan server menggunakan HTTP, di mana data biasanya dikirim dalam format JSON
```

### 3. Apa itu HTTP?

Jawaban:

```text
HTTP adalah protokol komunikasi yang digunakan untuk pertukaran data antara client (browser/aplikasi) dan server di internet
```

### 4. Apa itu JSON?

Jawaban:

```text
JSON adalah format data ringan yang digunakan untuk mengirim dan menerima data dalam bentuk teks yang mudah dibaca
```

### 5. Apa itu endpoint?

Jawaban:

```text
Endpoint adalah URL atau alamat API yang digunakan untuk mengakses suatu resource atau layanan di server
```

### 6. Apa itu request?

Jawaban:

```text
Request adalah permintaan yang dikirim oleh client ke server untuk mengambil atau mengirim data
```

### 7. Apa itu response?

Jawaban:

```text
Response adalah jawaban dari server terhadap request yang berisi data atau status hasil proses
```

### 8. Apa fungsi GET?

Jawaban:

```text
GET digunakan untuk mengambil atau membaca data dari server
```

### 9. Apa fungsi POST?

Jawaban:

```text
POST digunakan untuk mengirim data baru ke server untuk dibuat
```

### 10. Apa fungsi PUT?

Jawaban:

```text
PUT digunakan untuk memperbarui data yang sudah ada di server
```

### 11. Apa fungsi DELETE?

Jawaban:

```text
DELETE digunakan untuk menghapus data dari server
```

### 12. Kapan menggunakan 200 OK?

Jawaban:

```text
200 OK digunakan ketika request berhasil diproses dan data berhasil dikembalikan
```

### 13. Kapan menggunakan 201 Created?

Jawaban:

```text
201 Created digunakan ketika data baru berhasil dibuat di server
```

### 14. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
400 Bad Request digunakan ketika request dari client tidak valid atau formatnya salah
```

### 15. Kapan menggunakan 404 Not Found?

Jawaban:

```text
404 Not Found digunakan ketika resource atau data yang diminta tidak ditemukan
```

### 16. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
500 Internal Server Error digunakan ketika terjadi kesalahan pada server
```

### 17. Apa itu path parameter?

Jawaban:

```text
Path parameter adalah parameter yang dikirim melalui URL untuk menentukan resource tertentu
```

### 18. Apa itu query parameter?

Jawaban:

```text
Query parameter adalah parameter tambahan pada URL yang biasanya digunakan untuk filter atau pencarian data
```

### 19. Apa itu request body?

Jawaban:

```text
Request body adalah data yang dikirim oleh client ke server, biasanya dalam format JSON
```

### 20. Apa tugas Controller?

Jawaban:

```text
Controller bertugas menerima request dari client dan mengatur response yang akan dikirimkan
```

### 21. Apa tugas Service?

Jawaban:

```text
Service bertugas menjalankan business logic atau proses utama dari aplikasi
```

### 22. Apa itu DTO?

Jawaban:

```text
DTO (Data Transfer Object) adalah objek yang digunakan untuk mengirim data antara client dan server
```

### 23. Apa itu Model?

Jawaban:

```text
Model adalah representasi data atau struktur tabel yang digunakan dalam aplikasi
```

### 24. Kenapa business logic tidak ditaruh di Controller?

Jawaban:

```text
Agar kode lebih rapi, mudah di-maintain, dan mengikuti prinsip pemisahan tanggung jawab
```

### 25. Kenapa request/response perlu DTO?

Jawaban:

```text
Agar data yang dikirim lebih aman, terstruktur, dan tidak langsung expose model ke client
```