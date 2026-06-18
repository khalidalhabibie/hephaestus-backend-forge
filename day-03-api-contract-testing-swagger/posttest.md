# Posttest - API Contract, API Testing & Swagger

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari API Contract, API Testing, dan Swagger.

### 1. Apa itu API contract?

Jawaban:

```text
API contract adalah sebuah dokumentasi atau rancangan dari API yang akan dibuat dalam suatu program.
```

### 2. Apa saja isi API contract?

Jawaban:

```text
API contract penting untuk memastikan bahwa API yang dibuat sesuai dengan kesepakatan yang dibuat sejak awal dan untuk mempermudah developer dalam membangun API.
```

### 3. Kenapa API contract penting untuk frontend/mobile developer?

Jawaban:

```text
API contract penting karena menjadi acuan bersama antara frontend/mobile dan backend. Dengan contract, developer tahu format request, response, endpoint, status code, dan error yang digunakan, sehingga integrasi lebih jelas, konsisten, mudah dites, dan mengurangi risiko miskomunikasi atau bug.
```

### 4. Apa itu DTO?

Jawaban:

```text
DTO (Data Transfer Object) adalah object yang digunakan untuk membawa data antar layer atau antara client dan server
```

### 5. Apa bedanya request DTO dan response DTO?

Jawaban:

```text
Request DTO digunakan untuk menerima data dari client, sedangkan response DTO digunakan untuk mengirim data dari server ke client
```

### 6. Kenapa DTO dan model sebaiknya dipisah?

Jawaban:

```text
DTO dan model dipisah agar struktur data API tidak bergantung langsung pada struktur database, lebih aman, fleksibel, dan mudah dikontrol
```

### 7. Apa fungsi @JsonProperty?

Jawaban:

```text
@JsonProperty digunakan untuk mengatur nama field saat data Java diubah ke JSON atau sebaliknya
```

### 8. Apa fungsi @RequestBody?

Jawaban:

```text
@RequestBody digunakan untuk mengambil data dari body request, biasanya dalam format JSON, lalu mengubahnya menjadi object Java
```

### 9. Apa fungsi @PathVariable?

Jawaban:

```text
@PathVariable digunakan untuk mengambil nilai dari URL path, misalnya id pada /users/{id}
```

### 10. Apa fungsi @Valid?

Jawaban:

```text
@Valid digunakan untuk menjalankan validasi pada object request berdasarkan aturan validasi yang sudah dibuat
```

### 11. Apa perbedaan POST dan GET?

Jawaban:

```text
GET digunakan untuk mengambil data, sedangkan POST digunakan untuk mengirim atau membuat data baru
```

### 12. Apa perbedaan PUT dan PATCH?

Jawaban:

```text
PUT digunakan untuk update seluruh data, sedangkan PATCH digunakan untuk update sebagian data
```

### 13. Kapan menggunakan 201 Created?

Jawaban:

```text
201 Created digunakan ketika request berhasil membuat resource atau data baru
```

### 14. Kapan menggunakan 200 OK?

Jawaban:

```text
200 OK digunakan ketika request berhasil diproses, misalnya saat mengambil atau mengupdate data
```

### 15. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
400 Bad Request digunakan ketika request dari client tidak valid, misalnya format data salah atau field wajib kosong
```

### 16. Kapan menggunakan 404 Not Found?

Jawaban:

```text
404 Not Found digunakan ketika resource atau data yang diminta tidak ditemukan
```

### 17. Apa itu API testing?

Jawaban:

```text
API testing adalah proses menguji API untuk memastikan endpoint, request, response, status code, dan validasi berjalan sesuai kebutuhan
```

### 18. Apa saja yang perlu dicek saat API testing?

Jawaban:

```text
Yang perlu dicek adalah endpoint, method, request body, response body, status code, error handling, validasi, dan data yang dihasilkan
```

### 19. Apa perbedaan actual response dan expected response?

Jawaban:

```text
Expected response adalah hasil yang diharapkan, sedangkan actual response adalah hasil asli yang dikembalikan oleh API saat dites
```

### 20. Apa itu Swagger?

Jawaban:

```text
Swagger adalah tool untuk mendokumentasikan, melihat, dan mencoba API secara interaktif
```

### 21. Apa itu OpenAPI?

Jawaban:

```text
OpenAPI adalah standar spesifikasi untuk mendeskripsikan API, seperti endpoint, request, response, dan status code
```

### 22. Apa manfaat Swagger UI?

Jawaban:

```text
Swagger UI memudahkan developer membaca dokumentasi API dan mencoba endpoint langsung dari browser
```

### 23. URL apa yang digunakan untuk membuka Swagger UI?

Jawaban:

```text
Biasanya Swagger UI dibuka melalui URL /swagger-ui/index.html
```

### 24. URL apa yang digunakan untuk membuka OpenAPI JSON?

Jawaban:

```text
Biasanya OpenAPI JSON dibuka melalui URL /v3/api-docs
```

### 25. Apa fungsi @Tag?

Jawaban:

```text
@Tag digunakan untuk mengelompokkan endpoint di dokumentasi Swagger berdasarkan kategori tertentu
```

### 26. Apa fungsi @Operation?

Jawaban:

```text
@Operation digunakan untuk memberi deskripsi atau ringkasan pada sebuah endpoint di Swagger
```

### 27. Apa fungsi @ApiResponse?

Jawaban:

```text
@ApiResponse digunakan untuk mendokumentasikan kemungkinan response dari API, seperti status code dan deskripsinya
```

### 28. Apa perbedaan Swagger UI dan Postman?

Jawaban:

```text
Swagger UI digunakan untuk melihat dokumentasi dan mencoba API dari browser, sedangkan Postman lebih fokus untuk testing API secara manual atau otomatis
```

### 29. Apa yang harus dicek jika Swagger UI 404?

Jawaban:

```text
Cek dependency Swagger, konfigurasi aplikasi, URL Swagger UI, port aplikasi, dan apakah aplikasi berhasil berjalan
```

### 30. Bagian mana yang paling sulit dari Day 3?

Jawaban:

```text
Bagian paling sulit adalah memahami hubungan antara DTO, API contract, validasi, status code, dan dokumentasi Swagger
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Saya memahami pentingnya API contract sebagai acuan antara backend dan frontend/mobile.
2. Saya memahami penggunaan DTO untuk memisahkan data request/response dari model database.
3. Saya memahami dasar dokumentasi dan testing API menggunakan Swagger UI, OpenAPI, dan Postman.
```

Apa 2 hal yang masih membingungkan?

```text
1. Perbedaan penggunaan PUT dan PATCH dalam kasus nyata.
2. Cara menentukan status code yang paling tepat untuk setiap kondisi API.
```

Apa 1 pertanyaan untuk mentor?

```text
Bagaimana best practice membuat DTO dan response API yang rapi, konsisten, dan mudah digunakan oleh frontend/mobile developer?
```
