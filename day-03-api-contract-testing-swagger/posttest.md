# Posttest - API Contract, API Testing & Swagger

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari API Contract, API Testing, dan Swagger.

### 1. Apa itu API contract?

Jawaban:

```text
Dokumentasi API yang berisi status code, json (request dan response), link API untuk memudahkan backend dalam membuat endpoint, dan frontend untuk consume hasil dari endpoint yang sudah dibuat
```

### 2. Apa saja isi API contract?

Jawaban:

```text
Endpoint URL, HTTP method (GET/POST/dll), request (body, param, header), response (JSON), status code, dan deskripsi endpoint.

```

### 3. Kenapa API contract penting untuk frontend/mobile developer?

Jawaban:

```text
Agar frontend tahu cara menggunakan API tanpa menunggu backend selesai, dan mengurangi miskomunikasi antara backend dan frontend.
```

### 4. Apa itu DTO?

Jawaban:

```text
DTO (Data Transfer Object) adalah object yang digunakan untuk membawa data antara client dan server.
```

### 5. Apa bedanya request DTO dan response DTO?

Jawaban:

```text
Request DTO digunakan untuk menerima data dari client, sedangkan response DTO digunakan untuk mengirim data ke client.
```

### 6. Kenapa DTO dan model sebaiknya dipisah?

Jawaban:

```text
Agar keamanan dan fleksibilitas terjaga, serta tidak semua field di database terekspos ke client.
```

### 7. Apa fungsi @JsonProperty?

Jawaban:

```text
Untuk mengatur nama field JSON agar bisa berbeda dengan nama field di Java
```

### 8. Apa fungsi @RequestBody?

Jawaban:

```text
Untuk mengambil data dari body request (biasanya JSON) dan mengubahnya menjadi object Java.
```

### 9. Apa fungsi @PathVariable?

Jawaban:

```text
Untuk mengambil nilai dari URL path dan memasukkannya ke parameter method.
```

### 10. Apa fungsi @Valid?

Jawaban:

```text
Untuk melakukan validasi data pada request sesuai anotasi validasi (seperti @NotNull, @Email, dll).

```

### 11. Apa perbedaan POST dan GET?

Jawaban:

```text
GET digunakan untuk mengambil data, sedangkan POST digunakan untuk mengirim/membuat data baru.
```

### 12. Apa perbedaan PUT dan PATCH?

Jawaban:

```text
PUT digunakan untuk update seluruh data, sedangkan PATCH untuk update sebagian data.
```

### 13. Kapan menggunakan 201 Created?

Jawaban:

```text
Saat berhasil membuat resource/data baru.
```

### 14. Kapan menggunakan 200 OK?

Jawaban:

```text
Saat request berhasil dijalankan.
```

### 15. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
Saat request dari client tidak valid atau format salah.

```

### 16. Kapan menggunakan 404 Not Found?

Jawaban:

```text
Saat resource/data yang diminta tidak ditemukan.
```

### 17. Apa itu API testing?

Jawaban:

```text
Proses pengujian API untuk memastikan endpoint berjalan sesuai dengan yang diharapkan.
```

### 18. Apa saja yang perlu dicek saat API testing?

Jawaban:

```text
Status code, response body, response time, validasi data, dan error handling.
```

### 19. Apa perbedaan actual response dan expected response?

Jawaban:

```text
Actual response adalah hasil yang diterima dari API, sedangkan expected response adalah hasil yang diharapkan sesuai API contract.
```

### 20. Apa itu Swagger?

Jawaban:

```text
Tools untuk dokumentasi dan testing API secara otomatis.
```

### 21. Apa itu OpenAPI?

Jawaban:

```text
Standar specification untuk mendeskripsikan API REST.
```

### 22. Apa manfaat Swagger UI?

Jawaban:

```text
Untuk melihat dokumentasi API dan mencoba endpoint langsung dari browser.

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
Untuk mengelompokkan endpoint di Swagger.
```

### 26. Apa fungsi @Operation?

Jawaban:

```text
Untuk memberi deskripsi pada endpoint API.

```

### 27. Apa fungsi @ApiResponse?

Jawaban:

```text
Untuk mendeskripsikan response API termasuk status code dan pesan.

```

### 28. Apa perbedaan Swagger UI dan Postman?

Jawaban:

```text
Swagger UI digunakan untuk dokumentasi dan testing API di browser, sedangkan Postman adalah tools khusus untuk testing API yang lebih fleksibel.
```

### 29. Apa yang harus dicek jika Swagger UI 404?

Jawaban:

```text
Config dependency Swagger, URL endpoint, port server, dan apakah aplikasi sudah running.
```

### 30. Bagian mana yang paling sulit dari Day 3?

Jawaban:

```text
Penulisan code swagger yang banyak.
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. PENULISAN SWAGGER
2. AUTO GENERATE OPEN API DARI CODE
3. SERVICE PADA PATCH
```

Apa 2 hal yang masih membingungkan?

```text
1.
2.
```

Apa 1 pertanyaan untuk mentor?

```text

```
