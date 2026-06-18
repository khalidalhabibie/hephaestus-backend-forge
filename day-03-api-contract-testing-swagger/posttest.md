# Posttest - API Contract, API Testing & Swagger

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari API Contract, API Testing, dan Swagger.

### 1. Apa itu API contract?

Jawaban:

```text
API contract adalah kesepakatan antara client dan backend mengenai cara API digunakan, termasuk endpoint, request, response, status code, dan aturan lainnya.
```

### 2. Apa saja isi API contract?

Jawaban:

```text
HTTP method, endpoint URL, request body, response body, status code, dan error response.
```

### 3. Kenapa API contract penting untuk frontend/mobile developer?

Jawaban:

```text
Karena frontend dan mobile developer membutuhkan informasi yang jelas mengenai data yang harus dikirim dan data yang akan diterima dari backend.
```

### 4. Apa itu DTO?

Jawaban:

```text
DTO atau Data Transfer Object adalah object yang digunakan untuk mengirim dan menerima data antara client dan aplikasi. DTO membantu memisahkan data API dengan model internal aplikasi.
```

### 5. Apa bedanya request DTO dan response DTO?

Jawaban:

```text
Request DTO adalah objek yang menampung data yang dikirim oleh client ke backend.
Response DTO adalah objek yang menampung data yang dikirim oleh backend ke client.
```

### 6. Kenapa DTO dan model sebaiknya dipisah?

Jawaban:

```text
Agar struktur data untuk API terpisah dari model internal aplikasi dan lebih aman untuk digunakan. Selain itu, model adalah sesuatu yang jarang diubah sehingga jika ada yang perlu diubah di DTO tidak akan mempengaruhi model.
```

### 7. Apa fungsi @JsonProperty?

Jawaban:

```text
@JsonProperty digunakan untuk menghubungkan nama field JSON dengan nama field di Java. Misalnya JSON menggunakan full_name sedangkan Java menggunakan fullName.
```

### 8. Apa fungsi @RequestBody?

Jawaban:

```text
@RequestBody digunakan untuk mengambil data dari request body dan mengubahnya menjadi object Java.
```

### 9. Apa fungsi @PathVariable?

Jawaban:

```text
@PathVariable digunakan untuk mengambil nilai parameter yang terdapat pada URL.
```

### 10. Apa fungsi @Valid?

Jawaban:

```text
@Valid digunakan untuk menjalankan proses validasi pada request object (DTO) saat diterima oleh Controller.
```

### 11. Apa perbedaan POST dan GET?

Jawaban:

```text
POST digunakan untuk membuat data baru, sedangkan GET digunakan untuk mengambil data yang sudah ada.
```

### 12. Apa perbedaan PUT dan PATCH?

Jawaban:

```text
PUT digunakan untuk memperbarui seluruh data, sedangkan PATCH digunakan untuk memperbarui sebagian data saja.
```

### 13. Kapan menggunakan 201 Created?

Jawaban:

```text
Ketika request berhasil membuat resource atau data baru.
```

### 14. Kapan menggunakan 200 OK?

Jawaban:

```text
Ketika request berhasil diproses dan mengembalikan hasil dengan sukses.
```

### 15. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
Ketika request yang dikirim client tidak valid atau tidak sesuai format yang diharapkan.
```

### 16. Kapan menggunakan 404 Not Found?

Jawaban:

```text
Ketika resource atau data yang diminta tidak ditemukan.
```

### 17. Apa itu API testing?

Jawaban:

```text
API testing adalah proses menguji API untuk memastikan request dan response bekerja sesuai yang diharapkan.
```

### 18. Apa saja yang perlu dicek saat API testing?

Jawaban:

```text
Status code, response body, format data, error response, dan perilaku API sesuai requirement.
```

### 19. Apa perbedaan actual response dan expected response?

Jawaban:

```text
Expected response adalah hasil yang diharapkan, sedangkan actual response adalah hasil yang benar-benar dikembalikan oleh API saat dijalankan.
```

### 20. Apa itu Swagger?

Jawaban:

```text
Swagger adalah tools yang digunakan untuk membuat API Contract secara otomatis.
```

### 21. Apa itu OpenAPI?

Jawaban:

```text
OpenAPI adalah standarisasi yang digunakan untuk mendefinisikan REST API secara terstruktur.
```

### 22. Apa manfaat Swagger UI?

Jawaban:

```text
Swagger UI membantu developer melihat dokumentasi API dan mencoba endpoint langsung dari browser.
```

### 23. URL apa yang digunakan untuk membuka Swagger UI?

Jawaban:

```text
http://localhost:8080/swagger-ui.html
```

### 24. URL apa yang digunakan untuk membuka OpenAPI JSON?

Jawaban:

```text
http://localhost:8080/v3/api-docs
```

### 25. Apa fungsi @Tag?

Jawaban:

```text
@Tag digunakan untuk memberi nama dan mengelompokkan endpoint API di Swagger.
```

### 26. Apa fungsi @Operation?

Jawaban:

```text
@Operation digunakan untuk memberikan informasi mengenai suatu endpoint API.
```

### 27. Apa fungsi @ApiResponse?

Jawaban:

```text
@ApiResponse digunakan untuk mendokumentasikan kemungkinan response dan status code dari suatu endpoint.
```

### 28. Apa perbedaan Swagger UI dan Postman?

Jawaban:

```text
Swagger UI digunakan untuk dokumentasi dan testing sederhana yang dibuat otomatis dari aplikasi, sedangkan Postman digunakan untuk testing API yang secara manual namun lebih fleksibel dan dapat menyimpan banyak skenario pengujian.
```

### 29. Apa yang harus dicek jika Swagger UI 404?

Jawaban:

```text
Periksa apakah dependency Swagger sudah ditambahkan, aplikasi sudah direstart, URL yang digunakan benar, dan versi Spring Boot sesuai dengan versi Swagger yang digunakan.
```

### 30. Bagian mana yang paling sulit dari Day 3?

Jawaban:

```text
Memahami hubungan antara API contract, DTO, Swagger, dan bagaimana semuanya harus tetap konsisten agar frontend dan backend dapat berkomunikasi dengan baik.
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. API contract adalah kesepakatan antara client dan backend yang harus dijaga konsistensinya.
2. DTO membantu memisahkan struktur data API dari model internal aplikasi.
3. Swagger dan OpenAPI membantu dokumentasi serta mempermudah testing API.
```

Apa 2 hal yang masih membingungkan?

```text
1. Terkadang masih bingung untuk mencari sumber kesalahan code ketika saat testing terdapat bagian yang tidak sesuai dengan API Contract.
```

Apa 1 pertanyaan untuk mentor?

```text
Bagaimana cara memastikan Swagger, API contract, dan implementasi backend selalu sinkron ketika aplikasi terus berkembang?
```