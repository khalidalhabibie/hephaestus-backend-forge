# Posttest - API Contract, API Testing & Swagger

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari API Contract, API Testing, dan Swagger.

### 1. Apa itu API contract?

Jawaban:

```text
API contract adalah kesepakatan tertulis (spesifikasi) antara penyedia dan konsumen layanan yang menjelaskan endpoint, HTTP method, format request/response, status code, header, autentikasi, dan contoh penggunaan.

```

### 2. Apa saja isi API contract?

Jawaban:

```text
Isi API contract biasanya meliputi: daftar endpoint, HTTP method, format request body, format response body, contoh request/response, status code yang mungkin dikembalikan, header yang digunakan, autentikasi/otorisasi, dan aturan validasi/error format.
```

### 3. Kenapa API contract penting untuk frontend/mobile developer?

Jawaban:

```text
Karena API contract berfungsi sebagai acuan bersama agar backend dan frontend/mobile dapat terintegrasi dengan konsisten dan aman.
```

### 4. Apa itu DTO?

Jawaban:

```text
DTO (Data Transfer Object) adalah objek sederhana yang digunakan untuk mengangkut data antar lapisan aplikasi atau antar layanan tanpa logika bisnis.
```

### 5. Apa bedanya request DTO dan response DTO?

Jawaban:

```text
Request DTO = data yang masuk ke backend.
Response DTO = data yang keluar dari backend.
```

### 6. Kenapa DTO dan model sebaiknya dipisah?

Jawaban:

```text
Agar struktur data yang dikirim ke client tidak langsung bergantung pada model internal aplikasi, sehingga lebih aman, mudah diubah, dan hanya data yang diperlukan saja yang diekspos.
```

### 7. Apa fungsi @JsonProperty?

Jawaban:

```text
digunakan untuk memetakan nama field JSON ke field Java, terutama jika format penamaannya berbeda, misalnya snake_case di JSON dan camelCase di Java.
```

### 8. Apa fungsi @RequestBody?

Jawaban:

```text
digunakan untuk mengambil data dari body request (biasanya JSON) dan mengubahnya menjadi objek Java secara otomatis.
```

### 9. Apa fungsi @PathVariable?

Jawaban:

```text
digunakan untuk mengambil nilai yang ada di URL dan memasukkannya ke parameter method.
```

### 10. Apa fungsi @Valid?

Jawaban:

```text
digunakan untuk menjalankan validasi pada data yang diterima sesuai aturan yang sudah dibuat,
```

### 11. Apa perbedaan POST dan GET?

Jawaban:

```text
GET digunakan untuk mengambil data dari server.
POST digunakan untuk mengirim atau menambah data baru ke server.
```

### 12. Apa perbedaan PUT dan PATCH?

Jawaban:

```text
PUT menggantikan seluruh resource , sedangkan PATCH mengubah hanya field tertentu.
```

### 13. Kapan menggunakan 201 Created?

Jawaban:

```text
Gunakan 201 Created saat sebuah resource berhasil dibuat (biasanya setelah POST) dan sertakan header `Location` menuju resource baru.
```

### 14. Kapan menggunakan 200 OK?

Jawaban:

```text
Gunakan 200 OK saat permintaan berhasil dan server mengembalikan payload yang diminta (mis. GET atau operasi sukses lainnya).
```

### 15. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
Gunakan 400 Bad Request saat klien mengirim data yang tidak valid atau format permintaan salah.
```

### 16. Kapan menggunakan 404 Not Found?

Jawaban:

```text
Gunakan 404 Not Found saat resource yang diminta tidak ada di server.
```

### 17. Apa itu API testing?

Jawaban:

```text
API testing adalah proses memverifikasi bahwa endpoint API bekerja sesuai kontrak: status code, response schema, header, autentikasi, dan perilaku error.
```

### 18. Apa saja yang perlu dicek saat API testing?

Jawaban:

```text
Periksa status code, response body/schema, header, waktu respons, autentikasi/otorisasi, handling error, dan side effects pada database jika relevan.
```

### 19. Apa perbedaan actual response dan expected response?

Jawaban:

```text
Expected response adalah hasil yang diharapkan sesuai requirement atau API contract.
Actual response adalah hasil yang benar-benar diterima saat API dijalankan.
```

### 20. Apa itu Swagger?

Jawaban:

```text
Swagger adalah kumpulan tool (termasuk Swagger UI, Swagger Editor) yang bekerja dengan OpenAPI Spec untuk mendokumentasikan dan mencoba API secara interaktif.
```

### 21. Apa itu OpenAPI?

Jawaban:

```text
OpenAPI adalah spesifikasi standar berbentuk file (YAML/JSON) yang mendeskripsikan endpoint, schema, parameter, dan behavior sebuah REST API.
```

### 22. Apa manfaat Swagger UI?

Jawaban:

```text
Swagger UI menyediakan dokumentasi interaktif yang memungkinkan pengembang melihat spesifikasi dan mencoba endpoint langsung dari browser.
```

### 23. URL apa yang digunakan untuk membuka Swagger UI?

Jawaban:

```text
http://localhost:8080/swagger-ui/index.html
```

### 24. URL apa yang digunakan untuk membuka OpenAPI JSON?

Jawaban:

```text
http://localhost:8080/v3/api-docs
```

### 25. Apa fungsi @Tag?

Jawaban:

```text
digunakan untuk mengelompokkan dan memberi nama sekumpulan endpoint di Swagger agar dokumentasi API lebih rapi dan mudah dibaca.
```

### 26. Apa fungsi @Operation?

Jawaban:

```text
digunakan untuk memberikan deskripsi singkat pada suatu endpoint di Swagger/OpenAPI, sehingga pengguna tahu fungsi endpoint tersebut
```

### 27. Apa fungsi @ApiResponse?

Jawaban:

```text
digunakan untuk mendokumentasikan kemungkinan response dari suatu endpoint, seperti status code dan pesan yang akan dikembalikan
```

### 28. Apa perbedaan Swagger UI dan Postman?

Jawaban:

```text
Swagger UI digunakan untuk melihat dokumentasi API dan mencoba endpoint langsung dari browser.
Postman digunakan untuk testing API yang lebih lengkap dan fleksibel, seperti mengatur header, environment, collection, dan automation testing.
```

### 29. Apa yang harus dicek jika Swagger UI 404?

Jawaban:

```text
1. Apakah dependency Swagger sudah ditambahkan di pom.xml.
2. Apakah aplikasi Spring Boot sudah berhasil dijalankan.
3. Apakah URL yang dibuka sudah benar.
4. Apakah ada konfigurasi security yang memblokir Swagger.
5. Apakah versi Spring Boot dan Swagger yang digunakan cocok
```

### 30. Bagian mana yang paling sulit dari Day 3?

Jawaban:

```text
Yang sulit adalah menemukan error saya saat run program dan ada error terkait package namun sudah di benahi jadi package terkait nyambung swagger dan tentang API respon 
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. caranya menggunakan swagger
2. caranya buat API respon
3. tentang API contract dan API testing
```

Apa 2 hal yang masih membingungkan?

```text
1. lebih ke penggunaan syntax dan awal mengoding seperti apa
2. sudah mulai paham
```

Apa 1 pertanyaan untuk mentor?

```text
bagaimana biar lebih cepat memahami code atau suatu program
```
