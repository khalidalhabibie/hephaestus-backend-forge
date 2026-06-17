# Posttest - API Contract, API Testing & Swagger

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari API Contract, API Testing, dan Swagger.

### 1. Apa itu API contract?

Jawaban:

```text
kesepakatan antara backend dan frontend tentang bagaimana API digunakan (endpoint, request, response, dll)
```

### 2. Apa saja isi API contract?

Jawaban:

```text
endpoint, HTTP method, request, response, status code, dan format data
```

### 3. Kenapa API contract penting untuk frontend/mobile developer?

Jawaban:

```text
karena menjadi kesepakatan resmi antara frontend/mobile dan backend
```

### 4. Apa itu DTO?

Jawaban:

```text
object untuk transfer data antara layer (dari controller ke client)
```

### 5. Apa bedanya request DTO dan response DTO?

Jawaban:

```text
request DTO -> Objek yang dikirim dari client (frontend/mobile) ke backend

response DTO -> Objek yang dikirim dari backend ke client
```

### 6. Kenapa DTO dan model sebaiknya dipisah?

Jawaban:

```text
agar lebih aman, fleksibel, dan tidak terhubung ke database langsung
```

### 7. Apa fungsi @JsonProperty?

Jawaban:

```text
untuk mapping nama field JSON ke field di Java
```

### 8. Apa fungsi @RequestBody?

Jawaban:

```text
mengubah (deserialize) data dari format request (biasanya JSON) menjadi object (DTO) di backend
```

### 9. Apa fungsi @PathVariable?

Jawaban:

```text
mengambil data langsung dari URL (endpoint) dan mengubahnya menjadi parameter di method
```

### 10. Apa fungsi @Valid?

Jawaban:

```text
memastikan data request sesuai aturan validasi sebelum diproses oleh backend
```

### 11. Apa perbedaan POST dan GET?

Jawaban:

```text
- POST = membuat atau submit data
- GET = mengambil data
```

### 12. Apa perbedaan PUT dan PATCH?

Jawaban:

```text
- PUT = update semua field
- PATCH = hanya sebagian field
```

### 13. Kapan menggunakan 201 Created?

Jawaban:

```text
saat berhasil membuat data baru
```

### 14. Kapan menggunakan 200 OK?

Jawaban:

```text
saat request berhasil diproses
```

### 15. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
saat request tidak valid atau error dari client
```

### 16. Kapan menggunakan 404 Not Found?

Jawaban:

```text
saat data yang dicari tidak ditemukan
```

### 17. Apa itu API testing?

Jawaban:

```text
menguji API untuk memastikan berjalan sesuai fungsi
```

### 18. Apa saja yang perlu dicek saat API testing?

Jawaban:

```text
status code, response body, error handling, dan validasi data
```

### 19. Apa perbedaan actual response dan expected response?

Jawaban:

```text
- actual response = response yang benar-benar diterima dari sistem saat dijalankan

- expected response = response yang diharapkan sesuai dengan API contract
```

### 20. Apa itu Swagger?

Jawaban:

```text
tool untuk dokumentasi dan testing API secara otomatis
```

### 21. Apa itu OpenAPI?

Jawaban:

```text
standar spesifikasi untuk mendefinisikan API
```

### 22. Apa manfaat Swagger UI?

Jawaban:

```text
untuk melihat dan mencoba API langsung di browser
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
mengelompokkan endpoint API agar lebih rapi dan mudah dibaca di Swagger UI
```

### 26. Apa fungsi @Operation?

Jawaban:

```text
memberikan deskripsi detail pada satu endpoint API
```

### 27. Apa fungsi @ApiResponse?

Jawaban:

```text
mendokumentasikan response yang mungkin dikembalikan oleh suatu endpoint API
```

### 28. Apa perbedaan Swagger UI dan Postman?

Jawaban:

```text
Swagger untuk dokumentasi dan testing API, sedangkan Postman fokus untuk testing API saja
```

### 29. Apa yang harus dicek jika Swagger UI 404?

Jawaban:

```text
buka /v3/api-docs dan membenarkan di setup Swagger
```

### 30. Bagian mana yang paling sulit dari Day 3?

Jawaban:

```text
cukup banyak, karena saya tidak masuk pada hari jumat karena cuti wisuda sehingga saya ketinggalan materi dan merasa belum paham secara detail
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. cara mengecek API menggunakan postman
2. cara springboot bekerja
```

Apa 2 hal yang masih membingungkan?

```text
1. syntax yang digunakan
2. menentukan saya harus menambahkan pada file yang mana
```

Apa 1 pertanyaan untuk mentor?

```text
apakah saya bisa menanyakan materi yang tertinggal secara privat? karena jika dikelas saya merasa diburu waktu untuk memahami (tidak semua materi masuk dengan jelas, karena pace-nya cepat)
```