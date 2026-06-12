# Pretest - Spring Boot REST API

Jawab pertanyaan berikut dengan bahasa sendiri sebelum membaca materi Spring Boot REST API.

### 1. Apa itu Spring Boot?

Jawaban:

```text
framework di Java yang digunakan untuk membuat aplikasi (terutama backend/API)
```

### 2. Apa itu REST API?

Jawaban:

```text
cara berkomunikasi antara aplikasi lewat internet menggunakan HTTP dan ada request-response
```

### 3. Apa itu HTTP?

Jawaban:

```text
protokol komunikasi yang digunakan untuk pertukaran data antara client dan server
```

### 4. Apa itu JSON?

Jawaban:

```text
ormat data yang ringan dan mudah dibaca untuk mengirim data antar aplikasi dalam bentuk key-value / object
```

### 5. Apa itu endpoint?

Jawaban:

```text
alamat URL yang digunakan untuk mengakses API
```

### 6. Apa itu request?

Jawaban:

```text
 permintaan yang dikirim oleh client ke server untuk meminta data atau melakukan aksi
```

### 7. Apa itu response?

Jawaban:

```text
balasan dari server ke client setelah menerima request
```

### 8. Apa fungsi GET?

Jawaban:

```text
GET untuk mengambil data dari server
```

### 9. Apa fungsi POST?

Jawaban:

```text
POST untuk mengirim data baru ke server
```

### 10. Apa fungsi PUT?

Jawaban:

```text
PUT untuk mengupdate atau mengganti data yang sudah ada
```

### 11. Apa fungsi DELETE?

Jawaban:

```text
DELETE untuk menghapus data dari server
```

### 12. Kapan menggunakan 200 OK?

Jawaban:

```text
ketika request berhasil dan data berhasil dikembalikan
```

### 13. Kapan menggunakan 201 Created?

Jawaban:

```text
digunakan ketika berhasil membuat data baru di server
```

### 14. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
ketika request dari client salah atau tidak valid.
```

### 15. Kapan menggunakan 404 Not Found?

Jawaban:

```text
ketika data atau endpoint yang dicari tidak ditemukan
```

### 16. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
ketika terjadi kesalahan di server
```

### 17. Apa itu path parameter?

Jawaban:

```text
parameter yang ada di dalam URL (/users/1 <- 1 adalah path)
```

### 18. Apa itu query parameter?

Jawaban:

```text
parameter tambahan di URL untuk filter atau pencarian data
```

### 19. Apa itu request body?

Jawaban:

```text
data yang dikirim oleh client ke server
```

### 20. Apa tugas Controller?

Jawaban:

```text
menerima request dari client dan mengatur response yang dikembalikan
```

### 21. Apa tugas Service?

Jawaban:

```text
mengelola business logic atau logika utama dari aplikasi
```

### 22. Apa itu DTO?

Jawaban:

```text
objek yang digunakan untuk mengirim data antara client dan server
```

### 23. Apa itu Model?

Jawaban:

```text
representasi data atau struktur data yang digunakan di aplikasi (biasanya sesuai dengan tabel database)
```

### 24. Kenapa business logic tidak ditaruh di Controller?

Jawaban:

```text
supaya kode lebih rapi, mudah dipelihara, dan tidak berantakan. Controller cukup menerima request, sedangkan logika dipisah di Service.
```

### 25. Kenapa request/response perlu DTO?

Jawaban:

```text
supaya data lebih terstruktur, aman, dan tidak langsung expose data internal (model/database) ke client.
```
