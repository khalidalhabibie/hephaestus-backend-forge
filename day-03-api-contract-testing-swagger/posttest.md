# Posttest - API Contract, API Testing & Swagger

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari API Contract, API Testing, dan Swagger.

### 1. Apa itu API contract?

Jawaban:

```text
API contract ialah kontrak yang dibangun untuk aturan request dan response dari endpoint-endpoint API
```

### 2. Apa saja isi API contract?

Jawaban:

```text
List endpoint API yang disediakan BackEnd yang masing-masing endpoint berisikan aturan request body, params-nya, dan response. Lalu contoh request body dan response-nya juga
```

### 3. Kenapa API contract penting untuk frontend/mobile developer?

Jawaban:

```text
Agar FrontEnd/Mobile Developer tau aturan dan panduan untuk melakukan action CRUD ke endpoint yang disediakan BackEnd
```

### 4. Apa itu DTO?

Jawaban:

```text
Data Transfer Object, class yang digunakan untuk menangani response dan request dari controller (pintu masuk client ke API)
```

### 5. Apa bedanya request DTO dan response DTO?

Jawaban:

```text
Kalau Request DTO ialah DTO yang meng-handle request body yang didapatkan dari request body API, sedangkan Response DTO ialah DTO yang meng-handle data dari API untuk dikirim ke client sebagai response/jawaban
```

### 6. Kenapa DTO dan model sebaiknya dipisah?

Jawaban:

```text
Karena tujuan penggunaannya beda. DTO untuk meng-handle request dan response client, dan Model sendiri merupakan representasi tabel di database yang dibuat dalam bentuk kode pemrograman
```

### 7. Apa fungsi @JsonProperty?

Jawaban:

```text
Anotasi yang digunakan untuk mengubah key response ke API yang awalnya camelCase menjadi snake_case
```

### 8. Apa fungsi @RequestBody?

Jawaban:

```text
Sebagai anotasi yang ditambahkan di parameter controller untuk menandai data yang dikirim dari client dibaca sebagai bentuk request body
```

### 9. Apa fungsi @PathVariable?

Jawaban:

```text
Anotasi yang berfungsi untuk mengambil nilai dari URL dan mengikatnya ke parameter method di controller
```

### 10. Apa fungsi @Valid?

Jawaban:

```text
Agar parameter request body di controller bisa membaca dan menjalankan aturan dari anotasi yang ada di DTO, seperti @NotBlank, @NotNull, @Email, dan sebagainya
```

### 11. Apa perbedaan POST dan GET?

Jawaban:

```text
POST ialah method untuk menambahkan data baru, sedangkan GET ialah method untuk mendapatkan data
```

### 12. Apa perbedaan PUT dan PATCH?

Jawaban:

```text
PUT ialah method yang digunakan untuk mengganti seluruh resource, sedangkan PATCH ialah method yang digunakan untuk memperbarui sebagian resource saja
```

### 13. Kapan menggunakan 201 Created?

Jawaban:

```text
Ketika data berhasil ditambahkan
```

### 14. Kapan menggunakan 200 OK?

Jawaban:

```text
Ketika API me-response ke client dengan baik, biasanya digunakan untuk GET
```

### 15. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
Ketika request body yang dikirim dari client tidak sesuai dengan format yang disediakan dari BackEnd
```

### 16. Kapan menggunakan 404 Not Found?

Jawaban:

```text
Ketika data yang diminta oleh user tidak ditemukan, biasanya untuk method GET by id
```

### 17. Apa itu API testing?

Jawaban:

```text
Testing yang dilakukan apakah API sudah valid dan tidak ada error
```

### 18. Apa saja yang perlu dicek saat API testing?

Jawaban:

```text
Response dan request dari API sudah sesuai dengan yang ada di API contract
```

### 19. Apa perbedaan actual response dan expected response?

Jawaban:

```text
Actual Response ialah response yang dikeluarkan dari API, sedangkan Expected Response ialah harapan/ekspektasi response yang harusnya keluar dari API
```

### 20. Apa itu Swagger?

Jawaban:

```text
Tools untuk API testing berbasi otomatisasi dengan menambahkan anotasi pada tiap method-method di controller
```

### 21. Apa itu OpenAPI?

Jawaban:

```text
Standar spesifikasi untuk mendefinisikan dan mendokumentasikan REST API secara terstruktur
```

### 22. Apa manfaat Swagger UI?

Jawaban:

```text
Untuk memudahkan dalam testing request dan response dari API yang dibuat
```

### 23. URL apa yang digunakan untuk membuka Swagger UI?

Jawaban:

```text
`url (localhost)`/swagger-ui/index.html
```

### 24. URL apa yang digunakan untuk membuka OpenAPI JSON?

Jawaban:

```text
`url (localhost)`/v3/api-docs
```

### 25. Apa fungsi @Tag?

Jawaban:

```text
Anotasi yang digunakan untuk mengelompokkan endpoint API ke dalam kategori tertentu
```

### 26. Apa fungsi @Operation?

Jawaban:

```text
Anotasi yang digunakan untuk memberikan deskripsi detail mengenai suatu endpoint API, seperti tujuan dan ringkasannya
```

### 27. Apa fungsi @ApiResponse?

Jawaban:

```text
Anotasi yang digunakan untuk mendefinisikan kemungkinan respons dari endpoint API, termasuk kode status HTTP dan deskripsi responsnya. Seperti 200, 201, 400, dan berbagai jenis HTTP method lainnya
```

### 28. Apa perbedaan Swagger UI dan Postman?

Jawaban:

```text
Swagger UI adalah halaman yang ditambahkan di project yang bisa diakses berbasis web dan berguna sebagai dokumentasi untuk melihat dan mencoba endpoint API, sedangkan Postman adalah aplikasi tersendiri untuk menguji dan mengirim request API secara langsung
```

### 29. Apa yang harus dicek jika Swagger UI 404?

Jawaban:

```text
Mengecek dependency apakah sudah valid, baik itu artifactId-nya maupun versionId-nya atau apapun potensi error lainnya
```

### 30. Bagian mana yang paling sulit dari Day 3?

Jawaban:

```text
- Membuat API Contract dengan rapi dan tentunya best practice
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Penggunaan Swagger
2. Pemahaman seputar API Docs
3. Penggunaan created_at dan updated_at
```

Apa 2 hal yang masih membingungkan?

```text
1. Best practice API Contract
2. Best practice dokumentasi untuk Swagger UI
```

Apa 1 pertanyaan untuk mentor?

```text
Bagaimana best practice untuk API Contract? Apa saja elemen yang harus ada?
```
